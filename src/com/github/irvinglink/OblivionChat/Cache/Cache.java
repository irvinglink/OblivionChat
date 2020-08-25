package com.github.irvinglink.OblivionChat.Cache;

import java.util.Objects;
import java.util.UUID;

class Cache {

    private UUID uuid;
    private String channelName;

    public Cache(UUID uuid, String channelName) {
        this.uuid = uuid;
        this.channelName = channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return "Cache [" + "uuid=" + uuid + ", channelName= " + channelName + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cache cache = (Cache) o;
        return Objects.equals(uuid, cache.uuid) &&
                Objects.equals(channelName, cache.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, channelName);
    }

}
