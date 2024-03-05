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
package org.spin.mobile.service.dashboard;

import org.spin.mobile.service.settings.SettingsService;
import org.spin.proto.mobile.dashboard.GetStatisticsRequest;
import org.spin.proto.mobile.dashboard.GetStatisticsResponse;
import org.spin.proto.mobile.dashboard.Statistic;

public class DashboardService {
	public static GetStatisticsResponse getStatistics(GetStatisticsRequest request) {
		GetStatisticsResponse.Builder response = GetStatisticsResponse.newBuilder();
		Statistic.Builder data = Statistic.newBuilder();
		data.setConfig(SettingsService.getBaseSettings().getData());
//		data.addToday(StatisticData.newBuilder()
//				.setImage("https://hrm.onesttech.com/assets/app/dashboard/appoinment.png")
//				.setTitle("Appointments")
//				.setSlug("appointment")
//				.setNumber(0)
//				);
//		data.addCurrentMonth(StatisticData.newBuilder()
//				.setImage("https://hrm.onesttech.com/assets/app/dashboard/lateIn.png")
//				.setTitle("Late In")
//				.setSlug("late_in")
//				.setNumber(0));
		response.setData(data);
		return response.build();
	}
}
