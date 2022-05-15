/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ServerChannel;

/**
 * Event that is fired when a server channel is created.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamChannelCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamChannelCreated</a>
 */
public class TeamChannelCreatedEvent extends GuildedEvent
{
    private final ServerChannel channel;

    public TeamChannelCreatedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.channel = ServerChannel.fromString(j.getByPath("d.channel").toString());
    }

    /**
     * Gets the channel that was created.
     * @return The channel's ServerChannel object.
     */
    public ServerChannel getChannel(){return channel;}
}
