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
package org.spin.mobile_service.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 * Global changes for server
 */
public class GlobalValues {
	public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String MEDIUM_DATE_FORMAT = "dd MMM yyyy";
	public static final String MEDIUM_DATE_FORMAT_DD_MMM = "dd MMM";
	public static final String ONLY_DAY = "EEEE";
	
	
	public static String getFormattedDate(Timestamp date, String formatDate) {
		return new SimpleDateFormat(formatDate).format(date);
	}
}
