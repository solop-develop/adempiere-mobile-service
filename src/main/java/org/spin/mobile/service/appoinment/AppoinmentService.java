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
package org.spin.mobile.service.appoinment;

import org.spin.proto.mobile.appoinment.GetListRequest;
import org.spin.proto.mobile.appoinment.GetListResponse;
import org.spin.proto.mobile.appoinment.ListData;
import org.spin.proto.mobile.appoinment.ListItem;

public class AppoinmentService {
	
	public static GetListResponse getList(GetListRequest chekToken) {
		ListData.Builder data = ListData.newBuilder()
				.addItems(ListItem.newBuilder().setId(1).setTitle("New Year").setDate("January 1").setDay("Sunday").setStartDate("January 1, 2023").setAttachmentFileId("https://hrm.onesttech.com/static/blank_small.png"))
				;
		
		return GetListResponse.newBuilder()
				.setData(data)
				.setResult(true)
				.setEnv("local")
				.setMessage("Event List")
				.setStatus(200)
				.build();
	}
}
