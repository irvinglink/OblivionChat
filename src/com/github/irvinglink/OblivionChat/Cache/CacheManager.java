package com.github.irvinglink.OblivionChat.Cache;

class CacheManager {

    /*private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    public void writeFile(ArrayList<Cache> caches) throws CacheException {

        if (caches == null) return;

        try {
            FileWriter writer = new FileWriter(plugin.getCacheFile());

            Gson gson = new Gson();

            gson.toJson(caches, writer);

            writer.flush();

        } catch (IOException e) {
            throw new CacheException("There was an error while trying to register cache. Please report this error.");
        }

    }

    public void addNewCache(Cache cache) {

        ArrayList<Cache> cacheList = getCacheList();

        if (!cacheList.contains(cache)) {

            cacheList.add(cache);

            try {
                writeFile(cacheList);
            } catch (CacheException e) {
                e.printStackTrace();
            }

        }
    }

    public void removeCache(Cache cache) {

        ArrayList<Cache> cacheList = getCacheList();

        if (cacheList.contains(cache)) {
            cacheList.remove(cache);

            try {
                writeFile(cacheList);
            } catch (CacheException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean isUserCacheRegistered(UUID uuid) {

        List<Cache> list = getCacheList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getUniqueId().equals(uuid)) return true;
        }

        return false;

    }

    public Cache getUserCache(UUID uuid) {

        List<Cache> cacheList = getCacheList();

        for (int i = 0; i < cacheList.size(); i++) {
            if (cacheList.get(i).getUniqueId().equals(uuid)) return cacheList.get(i);
        }

        return null;
    }

    public ArrayList<Cache> getCacheList() {

        ArrayList<Cache> caches = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(plugin.getCacheFile()));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Cache>>() {
            }.getType();

            caches = gson.fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (caches == null) return new ArrayList<>();

        return caches;

    }

    @Deprecated
    public void writeArrayCache(JsonWriter writer, ArrayList<Cache> caches) throws IOException {
        writer.beginArray();

        caches.forEach(cache -> {
            try {
                addNewCache(writer, cache);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.endArray();
    }

    @Deprecated
    public void addNewCache(JsonWriter writer, Cache cache) throws IOException {

        assert cache != null;

        writer.beginObject();
        writer.name("UniqueId").value(cache.getUniqueId().toString());
        writer.name("Channel").value(cache.getChannelName());
        writer.endObject();

    }*/

}
