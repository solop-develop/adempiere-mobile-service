/************************************************************************************
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                     *
 * Contributor(s): Yamel Senih ysenih@erpya.com                                     *
 * This program is free software: you can redistribute it and/or modify             *
 * it under the terms of the GNU General Public License as published by             *
 * the Free Software Foundation, either version 2 of the License, or                *
 * (at your option) any later version.                                              *
 * This program is distributed in the hope that it will be useful,                  *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                   *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the                     *
 * GNU General Public License for more details.                                     *
 * You should have received a copy of the GNU General Public License                *
 * along with this program. If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.spin.mobile_service.service.settings;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.core.domains.models.I_AD_Form;
import org.compiere.model.MClient;
import org.compiere.model.MCurrency;
import org.compiere.model.MForm;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.spin.mobile.util.Changes;
import org.spin.proto.mobile.settings.BarikoiApi;
import org.spin.proto.mobile.settings.BaseSettings;
import org.spin.proto.mobile.settings.BaseSettingsData;
import org.spin.proto.mobile.settings.BreakStatus;
import org.spin.proto.mobile.settings.DutySchedule;
import org.spin.proto.mobile.settings.GetHomeScreenRequest;
import org.spin.proto.mobile.settings.HomeScreen;
import org.spin.proto.mobile.settings.HomeScreenData;
import org.spin.proto.mobile.settings.HomeScreenDataValue;
import org.spin.proto.mobile.settings.KeyValueData;
import org.spin.proto.mobile.settings.LiveTracking;
import org.spin.proto.mobile.settings.LocationServices;
import org.spin.proto.mobile.settings.TimeDefinition;
import org.spin.proto.mobile.settings.TimeWish;

public class SettingsService {
	
	private static String [] PERMISSIONS = {};

	private static String [] EMPLOYEE_TYPES = {
			"Permanent",
            "On Probation",
            "Contractual",
            "Intern"
			};

	private static String [] NOTIFICATION_CHANNELS = {};

	private static TimeWish.Builder getTextToShow() {
		Calendar calendar = Calendar.getInstance();
		int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

		if(timeOfDay >= 0 && timeOfDay < 12){
			return TimeWish.newBuilder().setWish("Good Morning").setSubTitle("Have a good morning").setImage("https://hrm.onesttech.com/assets/app/dashboard/good-morning.svg");        
		} else if(timeOfDay >= 16 && timeOfDay < 21){
			return TimeWish.newBuilder().setWish("Good Evening").setSubTitle("Thank you for your hard work today").setImage("https://hrm.onesttech.com/assets/app/dashboard/good-evening.svg");
		}
		return TimeWish.newBuilder().setWish("Good Night").setSubTitle("Have a good night").setImage("https://hrm.onesttech.com/assets/app/dashboard/good-night.svg");
	}
	
	public static BaseSettings.Builder getBaseSettings() {
		MUser user = MUser.get(Env.getCtx());
		MClient client = MClient.get(Env.getCtx(), user.getAD_Client_ID());
		MCurrency currency = MCurrency.get(Env.getCtx(), client.getC_Currency_ID());
		
		// Getting the time zone of calendar
		BaseSettingsData.Builder data = BaseSettingsData.newBuilder()
				.setIsAdmin(true)
				.setIsHr(true)
				.setIsManager(true)
				.setIsFaceRegistered(true)
				.setMultiCheckin(true)
				.setLocationBind(false)
				.setIsIpEnabled(false)
				//	Departments
				.addDepartments(KeyValueData.newBuilder().setId(1).setTitle("Management"))
				.addDepartments(KeyValueData.newBuilder().setId(2).setTitle("IT"))
				.addDepartments(KeyValueData.newBuilder().setId(3).setTitle("Sales"))
				//	Designations
				.addDesignations(KeyValueData.newBuilder().setId(1).setTitle("Admin"))
				.addDesignations(KeyValueData.newBuilder().setId(2).setTitle("HR"))
				.addDesignations(KeyValueData.newBuilder().setId(3).setTitle("Staff"))
				//	Employee types
				.addAllEmployeeTypes(Arrays.asList(EMPLOYEE_TYPES))
				//	Permissions
				.addAllPermissions(Arrays.asList(PERMISSIONS))
				//	Change this based on time
				.setTimeWish(getTextToShow())
//				.setTimeWish(TimeWish.newBuilder().setWish("Good Night").setSubTitle("Have a good night").setImage("https://hrm.onesttech.com/assets/app/dashboard/good-night.svg"))
//				.setTimeWish(TimeWish.newBuilder().setWish("Good Evening").setSubTitle("Thank you for your hard work today").setImage("https://hrm.onesttech.com/assets/app/dashboard/good-evening.svg"))
				//	Change by real timezone
				//	Europe/Tirane
				.setTimeZone(ZoneId.getAvailableZoneIds().stream().findFirst().get())
				//	Change by Client currency symbol
				.setCurrencySymbol(currency.getCurSymbol())
				.setCurrencyCode(currency.getISO_Code())
				.setAttendanceMethod("QR")
				.setDutySchedule(DutySchedule.newBuilder().setStartTime(TimeDefinition.newBuilder().setHour(16).setMin(55)).setEndTime(TimeDefinition.newBuilder().setHour(17)))
				.setLocationServices(LocationServices.newBuilder().setGoogle(true))
				.setBarikoiApi(BarikoiApi.newBuilder().setStatusId(4))
				.setBreakStatus(BreakStatus.newBuilder().setStatus("break_out"))
				.setLocationService(true)
				.setAppTheme("app_theme_1")
				.setIsTeamLead(true)
				.addAllNotificationChannels(Arrays.asList(NOTIFICATION_CHANNELS))
				.setLiveTracking(LiveTracking.newBuilder())
				;
		
		return BaseSettings.newBuilder()
				.setData(data)
				.setResult(true)
				.setMessage("Base settings information");
	}
	
	public static HomeScreen getHomeScreen(GetHomeScreenRequest request) {
		HomeScreenData.Builder data = HomeScreenData.newBuilder();
		AtomicInteger position = new AtomicInteger(1);
		new Query(Env.getCtx(), I_AD_Form.Table_Name, Changes.COLUMNNAME_MOBILE_IsMobile + " = 'Y' "
				+ "AND EXISTS(SELECT 1 FROM AD_Form_Access fa WHERE fa.AD_Form_ID = AD_Form.AD_Form_ID AND fa.AD_Role_ID = ?)", null)
		.setParameters(Env.getAD_Role_ID(Env.getCtx())).getIDsAsList().forEach(formId -> {
			MForm form = new MForm(Env.getCtx(), formId, null);
			String slug = form.get_ValueAsString(Changes.COLUMNNAME_MOBILE_Slug);
			if(!Util.isEmpty(slug)) {
				String imageUrl = Optional.ofNullable(form.get_ValueAsString(Changes.COLUMNNAME_MOBILE_ImageURL)).orElse("https://www.adempiere.io/assets/icon/logo.png");
				int extensionIndex = imageUrl.lastIndexOf(".") + 1;
				String imageType = "png";
				if(extensionIndex < imageUrl.length()) {
					imageType = imageUrl.substring(extensionIndex);
				}
				data.addData(HomeScreenDataValue.newBuilder()
						.setName(form.get_Translation(I_AD_Form.COLUMNNAME_Name))
						.setSlug(slug)
						.setPosition(position.getAndIncrement())
						.setIcon(imageUrl)
						.setImageType(imageType));
			}
		});
		return HomeScreen.newBuilder()
				.setData(data)
				.setResult(true)
				.setMessage("App home screen menus")
				.build();
	}
}
