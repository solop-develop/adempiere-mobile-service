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
package org.spin.mobile.service.settings;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;

import org.compiere.model.MClient;
import org.compiere.model.MCurrency;
import org.compiere.model.MUser;
import org.compiere.util.Env;
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
	
	private static String [] PERMISSIONS = {
			"hr_menu",
			"designation_read",
			"designation_create",
			"designation_update",
			"designation_delete",
			"department_read",
			"department_create",
			"department_update",
			"department_delete",
			"user_read",
			"profile_view",
			"user_create",
			"user_edit",
			"user_permission",
			"user_update",
			"user_delete",
			"user_menu",
			"make_hr",
			"profile_image_view",
			"user_device_list",
			"reset_device",
			"attendance_profile",
			"phonebook_profile",
			"support_ticket_profile",
			"advance_profile",
			"commission_profile",
			"appointment_profile",
			"visit_profile",
			"leave_request_profile",
			"notice_profile",
			"salary_profile",
			"project_profile",
			"task_profile",
			"award_profile",
			"travel_profile",
			"role_read",
			"role_create",
			"role_update",
			"role_delete",
			"branch_read",
			"branch_create",
			"branch_update",
			"branch_delete",
			"leave_type_read",
			"leave_type_create",
			"leave_type_update",
			"leave_type_delete",
			"leave_menu",
			"leave_assign_read",
			"leave_assign_create",
			"leave_assign_update",
			"leave_assign_delete",
			"daily_leave_read",
			"leave_request_read",
			"leave_request_update",
			"leave_request_store",
			"leave_request_create",
			"leave_request_approve",
			"leave_request_reject",
			"leave_request_delete",
			"weekend_read",
			"weekend_update",
			"holiday_read",
			"holiday_create",
			"holiday_update",
			"holiday_delete",
			"schedule_read",
			"schedule_create",
			"schedule_update",
			"schedule_delete",
			"attendance_read",
			"attendance_create",
			"attendance_update",
			"attendance_delete",
			"attendance_menu",
			"shift_read",
			"shift_create",
			"shift_update",
			"shift_delete",
			"shift_menu",
			"payroll_menu",
			"list_payroll_item",
			"create_payroll_item",
			"store_payroll_item",
			"edit_payroll_item",
			"update_payroll_item",
			"delete_payroll_item",
			"view_payroll_item",
			"payroll_item_menu",
			"list_payroll_set",
			"create_payroll_set",
			"store_payroll_set",
			"edit_payroll_set",
			"update_payroll_set",
			"delete_payroll_set",
			"view_payroll_set",
			"payroll_set_menu",
			"payslip_menu",
			"salary_generate",
			"salary_view",
			"salary_delete",
			"salary_edit",
			"salary_update",
			"salary_payment",
			"payslip_list",
			"announcement_menu",
			"notice_menu",
			"notice_list",
			"notice_edit",
			"notice_update",
			"notice_create",
			"notice_delete",
			"advance_type_menu",
			"advance_type_create",
			"advance_type_store",
			"advance_type_edit",
			"advance_type_update",
			"advance_type_delete",
			"advance_type_view",
			"advance_type_list",
			"advance_salaries_menu",
			"advance_salaries_create",
			"advance_salaries_store",
			"advance_salaries_edit",
			"advance_salaries_update",
			"advance_salaries_delete",
			"advance_salaries_view",
			"advance_salaries_approve",
			"advance_salaries_list",
			"advance_salaries_pay",
			"advance_salaries_invoice",
			"advance_salaries_search",
			"salary_menu",
			"salary_store",
			"salary_edit",
			"salary_update",
			"salary_delete",
			"salary_view",
			"salary_list",
			"salary_pay",
			"salary_invoice",
			"salary_approve",
			"salary_generate",
			"salary_calculate",
			"salary_search",
			"account_menu",
			"account_create",
			"account_store",
			"account_edit",
			"account_update",
			"account_delete",
			"account_view",
			"account_list",
			"account_search",
			"deposit_menu",
			"deposit_create",
			"deposit_store",
			"deposit_edit",
			"deposit_update",
			"deposit_delete",
			"deposit_list",
			"expense_menu",
			"expense_create",
			"expense_store",
			"expense_edit",
			"expense_update",
			"expense_delete",
			"expense_list",
			"expense_approve",
			"expense_invoice",
			"expense_pay",
			"expense_view",
			"deposit_category_menu",
			"deposit_category_create",
			"deposit_category_store",
			"deposit_category_edit",
			"deposit_category_update",
			"deposit_category_delete",
			"deposit_category_list",
			"payment_method_menu",
			"payment_method_create",
			"payment_method_store",
			"payment_method_edit",
			"payment_method_update",
			"payment_method_delete",
			"payment_method_list",
			"transaction_menu",
			"transaction_create",
			"transaction_store",
			"transaction_edit",
			"transaction_update",
			"transaction_delete",
			"transaction_view",
			"transaction_list",
			"project_menu",
			"project_create",
			"project_store",
			"project_edit",
			"project_update",
			"project_delete",
			"project_view",
			"project_list",
			"project_activity_view",
			"project_member_view",
			"project_member_delete",
			"project_complete",
			"project_payment",
			"project_invoice_view",
			"project_discussion_create",
			"project_discussion_store",
			"project_discussion_edit",
			"project_discussion_update",
			"project_discussion_delete",
			"project_discussion_view",
			"project_discussion_list",
			"project_discussion_comment",
			"project_discussion_reply",
			"project_file_create",
			"project_file_store",
			"project_file_edit",
			"project_file_update",
			"project_file_delete",
			"project_file_view",
			"project_file_list",
			"project_file_download",
			"project_file_comment",
			"project_file_reply",
			"project_notes_create",
			"project_notes_store",
			"project_notes_edit",
			"project_notes_update",
			"project_notes_delete",
			"project_notes_list",
			"project_files_comment",
			"general_settings_read",
			"general_settings_update",
			"email_settings_update",
			"storage_settings_update",
			"task_menu",
			"task_create",
			"task_store",
			"task_edit",
			"task_update",
			"task_delete",
			"task_view",
			"task_list",
			"task_activity_view",
			"task_assign_view",
			"task_assign_delete",
			"task_complete",
			"client_menu",
			"client_create",
			"client_store",
			"client_edit",
			"client_update",
			"client_delete",
			"client_view",
			"client_list",
			"task_discussion_create",
			"task_discussion_store",
			"task_discussion_edit",
			"task_discussion_update",
			"task_discussion_delete",
			"task_discussion_view",
			"task_discussion_list",
			"task_discussion_comment",
			"task_discussion_reply",
			"task_file_create",
			"task_file_store",
			"task_file_edit",
			"task_file_update",
			"task_file_delete",
			"task_file_view",
			"task_file_list",
			"task_file_download",
			"task_file_comment",
			"task_file_reply",
			"task_notes_create",
			"task_notes_store",
			"task_notes_edit",
			"task_notes_update",
			"task_notes_delete",
			"task_notes_list",
			"task_files_comment",
			"award_type_menu",
			"award_type_create",
			"award_type_store",
			"award_type_edit",
			"award_type_update",
			"award_type_delete",
			"award_type_list",
			"award_menu",
			"award_create",
			"award_store",
			"award_edit",
			"award_update",
			"award_delete",
			"award_view",
			"award_list",
			"travel_type_menu",
			"travel_type_create",
			"travel_type_store",
			"travel_type_edit",
			"travel_type_update",
			"travel_type_delete",
			"travel_type_list",
			"travel_menu",
			"travel_create",
			"travel_store",
			"travel_edit",
			"travel_update",
			"travel_delete",
			"travel_view",
			"travel_list",
			"travel_approve",
			"travel_payment",
			"meeting_menu",
			"meeting_create",
			"meeting_store",
			"meeting_edit",
			"meeting_update",
			"meeting_delete",
			"meeting_view",
			"meeting_list",
			"appointment_menu",
			"appointment_read",
			"appointment_create",
			"appointment_approve",
			"appointment_reject",
			"appointment_delete",
			"performance_menu",
			"performance_settings",
			"performance_indicator_menu",
			"performance_indicator_create",
			"performance_indicator_store",
			"performance_indicator_edit",
			"performance_indicator_update",
			"performance_indicator_delete",
			"performance_indicator_list",
			"performance_appraisal_menu",
			"performance_appraisal_create",
			"performance_appraisal_store",
			"performance_appraisal_edit",
			"performance_appraisal_update",
			"performance_appraisal_delete",
			"performance_appraisal_list",
			"performance_appraisal_view",
			"performance_goal_type_menu",
			"performance_goal_type_create",
			"performance_goal_type_store",
			"performance_goal_type_edit",
			"performance_goal_type_update",
			"performance_goal_type_delete",
			"performance_goal_type_list",
			"performance_goal_menu",
			"performance_goal_create",
			"performance_goal_store",
			"performance_goal_edit",
			"performance_goal_update",
			"performance_goal_delete",
			"performance_goal_view",
			"performance_goal_list",
			"performance_competence_type_menu",
			"performance_competence_type_create",
			"performance_competence_type_store",
			"performance_competence_type_edit",
			"performance_competence_type_update",
			"performance_competence_type_delete",
			"performance_competence_type_list",
			"performance_competence_menu",
			"performance_competence_create",
			"performance_competence_store",
			"performance_competence_edit",
			"performance_competence_update",
			"performance_competence_delete",
			"performance_competence_list",
			"attendance_report_read",
			"live_tracking_read",
			"report_menu",
			"leave_settings_read",
			"leave_settings_update",
			"ip_read",
			"ip_create",
			"ip_update",
			"ip_delete",
			"company_setup_menu",
			"company_setup_activation",
			"company_setup_activation_update",
			"company_setup_configuration",
			"company_setup_configuration_update",
			"company_setup_location",
			"company_settings_update",
			"location_create",
			"location_store",
			"location_edit",
			"location_update",
			"location_delete",
			"locationApi",
			"claim_read",
			"claim_create",
			"claim_update",
			"claim_delete",
			"payment_read",
			"payment_create",
			"payment_update",
			"payment_delete",
			"visit_menu",
			"visit_read",
			"visit_update",
			"visit_view",
			"app_settings_menu",
			"app_settings_update",
			"web_setup_menu",
			"content_menu",
			"content_read",
			"content_update",
			"content_delete",
			"menu",
			"menu_create",
			"menu_store",
			"menu_edit",
			"menu_update",
			"menu_delete",
			"service_menu",
			"service_create",
			"service_store",
			"portfolio_edit",
			"service_update",
			"service_delete",
			"portfolio_menu",
			"portfolio_create",
			"portfolio_store",
			"portfolio_edit",
			"portfolio_update",
			"portfolio_delete",
			"contact_menu",
			"contact_read",
			"contact_create",
			"contact_update",
			"contact_delete",
			"language_menu",
			"language_create",
			"language_edit",
			"language_update",
			"language_delete",
			"make_default",
			"setup_language",
			"team_member_menu",
			"team_member_read",
			"team_member_create",
			"team_member_store",
			"team_member_edit",
			"team_member_update",
			"team_member_delete",
			"support_menu",
			"support_read",
			"support_create",
			"support_reply",
			"support_delete"
			};

	private static String [] EMPLOYEE_TYPES = {
			"Permanent",
            "On Probation",
            "Contractual",
            "Intern"
			};

	private static String [] NOTIFICATION_CHANNELS = {
			"user1",
            "department1"
			};

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
		return HomeScreen.newBuilder()
				.setData(HomeScreenData.newBuilder()
						.addData(HomeScreenDataValue.newBuilder()
								.setName("Task")
								.setSlug("task")
								.setPosition(1)
								.setIcon("https://hrm.onesttech.com/assets/appScreenIcons/task.png")
								.setImageType("png")))
				.setResult(true)
				.setMessage("App home screen menus")
				.build();
	}
}
