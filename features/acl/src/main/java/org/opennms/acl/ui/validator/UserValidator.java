//============================================================================
//
// Copyright (c) 2009+ Massimiliano Dessi (desmax74)
// Copyright (c) 2009+ The OpenNMS Group, Inc.
// All rights reserved everywhere.
//
// This program was developed and is maintained by Massimiliano Dessi
// ("the author") and is subject to dual-copyright according to
// the terms set in "The OpenNMS Project Contributor Agreement".
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
// USA.
//
// The author can be contacted at the following email address:
//
//       Massimiliano Dessi
//       desmax74@yahoo.it
//
//
//-----------------------------------------------------------------------------
// OpenNMS Network Management System is Copyright by The OpenNMS Group, Inc.
//============================================================================
package org.opennms.acl.ui.validator;

import org.opennms.acl.service.UserService;
import org.opennms.netmgt.model.OnmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for new user
 * @author Massimiliano Dess&igrave; (desmax74@yahoo.it)
 * @since 1.9.0
 */
@Component("userValidator")
public class UserValidator implements Validator {

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return OnmsUser.class.isAssignableFrom(clazz);
    }

    public void validate(Object command, Errors err) {
        OnmsUser user = (OnmsUser) command;
        if(user.getNew()){
            newUser(err, user);
        }else{
        	passwordValidator.validate(user, err);
        }
    }

    private void newUser(Errors err, OnmsUser user) {

        ValidationUtils.rejectIfEmptyOrWhitespace(err, "username", "username.required.value", "username is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "password", "password.required.value", "password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "confirmNewPassword", "password.required.value", "password is required.");

        if (!user.getPassword().equals(user.getConfirmNewPassword())) {
			err.rejectValue("password", "error.password.match",
					"password don't match the confirm");
			err.rejectValue("confirmNewPassword", "error.confirmpassword.match",
					"confirm password don't match the new");
		}

        if (null != userService.getUser(user.getUsername())) {
            err.rejectValue("username", "error.username.already.present");
        }

        if (!user.getPassword().equals("") && user.getPassword().length() < 4) {
            err.rejectValue("password", "error.password.length", "password too short");
        }
    }

    @Autowired
	private PasswordValidator passwordValidator;
    @Autowired
	private UserService userService;
}
