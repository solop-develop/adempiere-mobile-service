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
package org.spin.mobile_service.service.upcoming_events;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.core.domains.models.I_R_Request;
import org.compiere.model.MRequest;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.spin.mobile_service.util.GlobalValues;
import org.spin.proto.mobile.upcoming_events.GetListRequest;
import org.spin.proto.mobile.upcoming_events.GetListResponse;
import org.spin.proto.mobile.upcoming_events.ListData;
import org.spin.proto.mobile.upcoming_events.ListItem;


public class UpcomingEventsService {
	
	public static GetListResponse getList(GetListRequest chekToken) {
		
		ListData.Builder data = ListData.newBuilder();
		getMonthlyTaskListIds().forEach(requestId -> {
			MRequest request = new MRequest(Env.getCtx(), requestId, null);
			Timestamp startDate = Optional.ofNullable(request.getStartDate()).orElse(request.getCreated());
			Timestamp endDate = request.getDateNextAction();
			if(endDate == null) {
				if(request.getCloseDate() != null) {
					endDate = request.getCloseDate();
				} else if(request.getDateCompletePlan() != null) {
					endDate = request.getDateCompletePlan();
				} else {
					endDate = Optional.ofNullable(request.getStartDate()).orElse(request.getCreated());
				}
			}
			data.addItems(ListItem.newBuilder()
					.setId(requestId)
					.setTitle(request.getDocumentNo() + (request.getSubject() != null ? ": " + request.getSubject(): ""))
					.setDate(GlobalValues.getFormattedDate(endDate, GlobalValues.MEDIUM_DATE_FORMAT_DD_MMM))
					.setDay(GlobalValues.getFormattedDate(endDate, GlobalValues.ONLY_DAY))
					.setStartDate(GlobalValues.getFormattedDate(startDate, GlobalValues.MEDIUM_DATE_FORMAT_DD_MMM))
					.setAttachmentFileId("https://hrm.onesttech.com/static/blank_small.png"));
		});
		return GetListResponse.newBuilder()
				.setData(data)
				.setResult(true)
				.setEnv("local")
				.setMessage("Event List")
				.setStatus(200)
				.build();
		
//		ListData.Builder data = ListData.newBuilder()
//				.addItems(ListItem.newBuilder().setId(1).setTitle("New Year").setDate("January 1").setDay("Sunday").setStartDate("January 1, 2023").setAttachmentFileId("https://hrm.onesttech.com/static/blank_small.png"))
//				;
//		
//		return GetListResponse.newBuilder()
//				.setData(data)
//				.setResult(true)
//				.setEnv("local")
//				.setMessage("Event List")
//				.setStatus(200)
//				.build();
	}
	
	private static List<Integer> getMonthlyTaskListIds() {
		List<Object> parameters = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer("SalesRep_ID = ? AND C_Project_ID IS NOT NULL AND EXISTS(SELECT 1 FROM R_Status s WHERE s.R_Status_ID = R_Request.R_Status_ID AND s.IsOpen = 'Y')");
		parameters.add(Env.getAD_User_ID(Env.getCtx()));
		return new Query(Env.getCtx(), I_R_Request.Table_Name, whereClause.toString(), null)
			.setParameters(parameters)
			.setClient_ID()
			.getIDsAsList();
	}
}
