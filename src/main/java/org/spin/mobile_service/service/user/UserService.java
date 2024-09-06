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
package org.spin.mobile_service.service.user;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.spin.model.MADUserSocialMedia;
import org.spin.proto.mobile.user.CheckinData;
import org.spin.proto.mobile.user.GetCheckinStatusRequest;
import org.spin.proto.mobile.user.GetCheckinStatusResponse;
import org.spin.proto.mobile.user.SetMessageTokenRequest;
import org.spin.proto.mobile.user.SetMessageTokenResponse;


public class UserService {
	
	/**	Firebase	*/
	public static final String APPLICATION_TYPE = "MFB";
	
	public static GetCheckinStatusResponse getCheckinStatus(GetCheckinStatusRequest chekToken) {
		
		CheckinData.Builder data = CheckinData.newBuilder()
				;
		
		return GetCheckinStatusResponse.newBuilder()
				.setData(data)
				.setResult(true)
				.setMessage("Please check in now")
				.build();
	}
	
	public static SetMessageTokenResponse setMessageToken(SetMessageTokenRequest request) {
		if(request.getUserId() <= 0) {
			throw new AdempiereException("@AD_User_ID@ @IsMandatory@");
		}
		if(Util.isEmpty(request.getFirebaseToken())) {
			throw new AdempiereException("@Token@ @IsMandatory@");
		}
		//	Get User
		MUser user = MUser.get(Env.getCtx(), request.getUserId());
		if(user == null) {
			throw new AdempiereException("@Token@ @IsMandatory@");	
		}
		//	
		if(user.isActive()) {
			int socialMediaId = getSocialMediaId(request.getUserId(), APPLICATION_TYPE);
			if(socialMediaId < 0) {
				socialMediaId = 0;
			}
			MADUserSocialMedia socialMedia = new MADUserSocialMedia(Env.getCtx(), socialMediaId, null);
			socialMedia.setAD_User_ID(request.getUserId());
			socialMedia.setApplicationType(APPLICATION_TYPE);
			socialMedia.setAccountName(request.getFirebaseToken());
			socialMedia.setIsReceiveNotifications(true);
			if(!Util.isEmpty(request.getDeviceName())) {
				socialMedia.setDescription(request.getDeviceName());
			}
			socialMedia.saveEx();
		}
		//	
		return SetMessageTokenResponse.newBuilder()
				.setResult(true)
				.setMessage("Token saved from user " + user.getName())
				.build();
	}
	
	private static int getSocialMediaId(int userId, String applicationType) {
		return new Query(Env.getCtx(), MADUserSocialMedia.Table_Name, "AD_User_ID = ? AND ApplicationType = ?", null)
				.setParameters(userId, applicationType)
				.setOnlyActiveRecords(true)
				.firstId();
	}
}
