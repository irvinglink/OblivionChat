package com.github.irvinglink.OblivionChat.Cache;

class CacheMonitor {

    /*private OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private ArrayList<Cache> exampleCacheList = new ArrayList<>();

    private File cacheFile;
    private File cacheFolder;

    public void setup() {

        this.cacheFolder = new File(plugin.getDataFolder() + File.separator + "temp");

        if (!cacheFolder.exists())
            cacheFolder.mkdirs();
        else {

            if (Objects.requireNonNull(cacheFolder.listFiles()).length != 0) {

                for (File file : Objects.requireNonNull(cacheFolder.listFiles())) {
                    file.delete();
                }
            }

        }


        try {

            Path path = Files.createTempFile(cacheFolder.toPath(), "cache", ".json");

            cacheFile = path.toFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getCacheFile() {
        if (cacheFile != null) return cacheFile;

        cacheFile = new File(cacheFolder, "cache.json");

        if (!cacheFile.exists()) {
            try {
                Files.copy(Objects.requireNonNull(plugin.getResource(cacheFile.getName())), cacheFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cacheFile;
    }*/

}
