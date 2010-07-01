/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2007 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Modifications:
 * 
 * Created: February 22, 2007
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact:
 *      OpenNMS Licensing       <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 */

package org.opennms.dashboard.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * <p>SurveillanceService interface.</p>
 *
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 * @version $Id: $
 * @since 1.8.1
 */
public interface SurveillanceService extends RemoteService {

    /**
     * <p>getSurveillanceData</p>
     *
     * @return a {@link org.opennms.dashboard.client.SurveillanceData} object.
     */
    public SurveillanceData getSurveillanceData();
    
    /**
     * <p>getAlarmsForSet</p>
     *
     * @param set a {@link org.opennms.dashboard.client.SurveillanceSet} object.
     * @return an array of {@link org.opennms.dashboard.client.Alarm} objects.
     */
    public Alarm[] getAlarmsForSet(SurveillanceSet set);
    
    /**
     * <p>getNotificationsForSet</p>
     *
     * @param set a {@link org.opennms.dashboard.client.SurveillanceSet} object.
     * @return an array of {@link org.opennms.dashboard.client.Notification} objects.
     */
    public Notification[] getNotificationsForSet(SurveillanceSet set);
    
    /**
     * <p>getNodeNames</p>
     *
     * @param set a {@link org.opennms.dashboard.client.SurveillanceSet} object.
     * @return an array of {@link java.lang.String} objects.
     */
    public String[] getNodeNames(SurveillanceSet set);
    
    /**
     * <p>getResources</p>
     *
     * @param set a {@link org.opennms.dashboard.client.SurveillanceSet} object.
     * @return an array of {@link java.lang.String} objects.
     */
    public String[][] getResources(SurveillanceSet set);
    
    /**
     * <p>getChildResources</p>
     *
     * @param resourceId a {@link java.lang.String} object.
     * @return an array of {@link java.lang.String} objects.
     */
    public String[][] getChildResources(String resourceId);
    
    /**
     * <p>getPrefabGraphs</p>
     *
     * @param resourceId a {@link java.lang.String} object.
     * @return an array of {@link java.lang.String} objects.
     */
    public String[][] getPrefabGraphs(String resourceId);
    
    /**
     * <p>getRtcForSet</p>
     *
     * @param set a {@link org.opennms.dashboard.client.SurveillanceSet} object.
     * @return an array of {@link org.opennms.dashboard.client.NodeRtc} objects.
     */
    public NodeRtc[] getRtcForSet(SurveillanceSet set);
}
