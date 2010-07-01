//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
package org.opennms.protocols.wmi.wbem;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: CE136452
 * Date: Oct 21, 2008
 * Time: 6:45:47 AM
 * To change this template use File | Settings | File Templates.
 *
 * @author ranger
 * @version $Id: $
 */
public enum OnmsWbemFlagReturnEnum {
   
    wbemFlagForwardOnly(32, "wbemFlagForwardOnly"),
    wbemFlagBidirectional(0, "wbemFlagBidirectional"),
    wbemFlagReturnImmediately(16, "wbemFlagReturnImmediately"),
    wbemFlagReturnWhenComplete(0, "wbemFlagReturnWhenComplete"),
    wbemQueryFlagPrototype(2, "wbemQueryFlagPrototype"),
    wbemFlagUseAmendedQualifiers(131072, "wbemFlagUseAmendedQualifiers");

    /** Constant <code>lookup</code> */
    private static final Map<Integer, OnmsWbemFlagReturnEnum> lookup = new HashMap<Integer, OnmsWbemFlagReturnEnum>();
    private int returnFlagValue;
    private String returnFlagName;

    static {
        for (OnmsWbemFlagReturnEnum s : EnumSet.allOf(OnmsWbemFlagReturnEnum.class))
            lookup.put(s.getReturnFlagValue(), s);
    }

    OnmsWbemFlagReturnEnum(int returnFlagValue, String returnFlagName) {
        this.returnFlagValue = returnFlagValue;
        this.returnFlagName = returnFlagName;
    }

    /**
     * <p>Getter for the field <code>returnFlagValue</code>.</p>
     *
     * @return a int.
     */
    public int getReturnFlagValue() { return returnFlagValue; }
    /**
     * <p>Getter for the field <code>returnFlagName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getReturnFlagName() { return returnFlagName; }

    /**
     * <p>get</p>
     *
     * @param returnFlagValue a int.
     * @return a {@link org.opennms.protocols.wmi.wbem.OnmsWbemFlagReturnEnum} object.
     */
    public static OnmsWbemFlagReturnEnum get(int returnFlagValue) {
          return lookup.get(returnFlagValue);
     }
}

