package pcl.opensecurity.client.sounds;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class AlarmResource implements IResourcePack {
    public static final String PACK_NAME = "opensecurity_external";
    public static Map<String,String> sound_map = new HashMap<String,String>();
    private static File mc_dir = Minecraft.getMinecraft().mcDataDir;

    public InputStream getInputStream(ResourceLocation l) throws IOException {
        if (l.getResourcePath().equals("sounds.json")) return generateSoundsJSON();
        return new FileInputStream(getRealPathBecauseMojangLiterallyCantEvenCodeOutsideTheirUsageScenario(l));
    }
    public boolean resourceExists(ResourceLocation l) {
        return isResourceFromThisPack(l) && (l.getResourcePath().equals("sounds.json") || getRealPathBecauseMojangLiterallyCantEvenCodeOutsideTheirUsageScenario(l).exists());
    }
    public Set<String> getResourceDomains() {
        return Sets.newHashSet(getPackName());
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        return null;
    }

    public BufferedImage getPackImage() throws IOException {
        return null; //don't need no image for this shit
    }
    public String getPackName() {
        return PACK_NAME;
    }

   /** HELPERS */

    private boolean isResourceFromThisPack (ResourceLocation l) {
        return l.getResourceDomain().equals(getPackName());
    }
    private File getRealPathBecauseMojangLiterallyCantEvenCodeOutsideTheirUsageScenario (ResourceLocation l) {
        String altPath = l.getResourcePath();
        altPath = altPath.substring(7); //omit the leading "sounds/"
        //System.out.println("Final path: "+new File(mc_dir.getAbsolutePath(),altPath).getAbsolutePath());
        return new File(mc_dir.getAbsolutePath(),altPath);
    }
    private static InputStream generateSoundsJSON () throws IOException {
        JsonObject root = new JsonObject();
        for (Map.Entry<String, String> entry : sound_map.entrySet()) {
            JsonObject event = new JsonObject();
            event.addProperty("category", "master"); // put under the "master" category for sound options
            JsonArray sounds = new JsonArray(); // array of sounds (will only ever be one)
            JsonObject sound = new JsonObject(); // sound object (instead of primitive to use 'stream' flag)
            sound.addProperty("name", File.separator+"mods"+File.separator+"OpenSecurity"+File.separator+"sounds"+File.separator+"alarms"+File.separator + entry.getValue().substring(0, entry.getValue().lastIndexOf('.'))); // path to file
            sound.addProperty("stream", false); // streaming seems to break the alarm... why?
            sounds.add(sound);
            event.add("sounds", sounds);
           root.add(entry.getValue().substring(0, entry.getValue().lastIndexOf('.')), event); // event name (same as name sent to ItemCustomRecord)
        }
        System.out.println(new Gson().toJson(root));
        return new ByteArrayInputStream(new Gson().toJson(root).getBytes());
    }

    public void addSoundReferenceMapping (int recordNum, String pathToSound) {
        sound_map.put("opensecurity"+recordNum,pathToSound);
    }

    public void registerAsResourceLocation () {
        //List<IResourcePack> values = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
       // values.add(this);
        //ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), values, "defaultResourcePacks", "field_110449_ao");
    	List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
    	defaultResourcePacks.add(this);
    }
   
}
