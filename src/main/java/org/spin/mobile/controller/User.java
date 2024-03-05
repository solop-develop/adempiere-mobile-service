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
package org.spin.mobile.controller;

import org.compiere.util.CLogger;
import org.spin.mobile.service.user.UserService;
import org.spin.proto.mobile.user.GetCheckinStatusRequest;
import org.spin.proto.mobile.user.GetCheckinStatusResponse;
import org.spin.proto.mobile.user.UserServiceGrpc.UserServiceImplBase;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class User extends UserServiceImplBase {
	
	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(User.class);
	
	@Override
	public void getCheckinStatus(GetCheckinStatusRequest request, StreamObserver<GetCheckinStatusResponse> responseObserver) {
		try {
			responseObserver.onNext(UserService.getCheckinStatus(request));
			responseObserver.onCompleted();
		} catch (Exception e) {
			log.severe(e.getLocalizedMessage());
			responseObserver.onError(Status.INTERNAL
					.withDescription(e.getLocalizedMessage())
					.withCause(e)
					.asRuntimeException());
		}
	}
}
