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
package org.spin.mobile_service.service.appoinment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.adempiere.core.domains.models.I_C_NonBusinessDay;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.spin.mobile_service.util.GlobalValues;
import org.spin.proto.mobile.appoinment.GetListRequest;
import org.spin.proto.mobile.appoinment.GetListResponse;
import org.spin.proto.mobile.appoinment.ListData;
import org.spin.proto.mobile.appoinment.ListItem;

public class AppoinmentService {
	
	public static GetListResponse getList(GetListRequest chekToken) {
		ListData.Builder data = ListData.newBuilder();
		new Query(Env.getCtx(), I_C_NonBusinessDay.Table_Name, "Date1 >= ?", null)
		.setParameters(new Timestamp(System.currentTimeMillis()))
		.setOrderBy(I_C_NonBusinessDay.COLUMNNAME_Date1)
		.getIDsAsList()
		.forEach(nonBusinessDayId -> {
			MNonBusinessDay nonBusinessDay = new MNonBusinessDay(Env.getCtx(), nonBusinessDayId, null);
			data.addItems(
					ListItem.newBuilder()
					.setId(nonBusinessDayId)
					.setTitle(Optional.ofNullable(nonBusinessDay.getName()).orElse("--"))
					.setDate(new SimpleDateFormat(GlobalValues.MEDIUM_DATE_FORMAT_DD_MMM).format(nonBusinessDay.getDate1()))
					.setDay(new SimpleDateFormat(GlobalValues.ONLY_DAY).format(nonBusinessDay.getDate1()))
					.setStartDate(new SimpleDateFormat(GlobalValues.MEDIUM_DATE_FORMAT_DD_MMM).format(nonBusinessDay.getDate1()))
					.setAttachmentFileId("https://hrm.onesttech.com/static/blank_small.png"))
			;
		});
		
		return GetListResponse.newBuilder()
				.setData(data)
				.setResult(true)
				.setEnv("local")
				.setMessage("Event List")
				.setStatus(200)
				.build();
	}
}
