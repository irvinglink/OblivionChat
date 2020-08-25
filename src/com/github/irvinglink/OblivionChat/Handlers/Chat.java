package com.github.irvinglink.OblivionChat.Handlers;

import com.github.irvinglink.OblivionChat.OblivionChat;
import com.github.irvinglink.OblivionChat.Placeholders.ReplacementHook;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    private final static Map<String, ReplacementHook> hookMap = new HashMap<>();
    private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");

    public String str(String textToTranslate) {
        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

    public void registerKeys() {
        hookMap.put("oblivion", new ReplacementHook());
    }

    public void sendConsoleLog(String str) {
        plugin.getServer().getLogger().info(str);
    }

    public TextComponent clickHoverMessage(String text, String hoverValue, String cmd) {

        TextComponent component = new TextComponent(text);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverValue).create()));

        return component;
    }

    public TextComponent hoverMessage(String text, String hoverValue) {
        TextComponent component = new TextComponent(text);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverValue).create()));

        return component;
    }


    public String replace(Player player, String text, boolean color) {
        return replace(player, null, null, text, color);
    }

    public String replace(Player player, Channel channel, String text, boolean color) {
        return replace(player, null, channel, text, color);
    }


    public String replace(Player player, Player target, String text, boolean color) {
        return replace(player, target, null, text, color);
    }

    public String replace(Player player, Player target, Channel channel, String text, boolean color) {

        if (text == null) return null;

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            String var = matcher.group(1);
            int index = var.indexOf("_");

            if (index <= 0 || index >= var.length()) continue;

            String beginning = var.substring(0, index);
            String next = var.substring(index + 1);

            if (hookMap.containsKey(beginning)) {

                String value = hookMap.get(beginning).replace(player, target, channel, next);

                if (value != null)
                    text = text.replaceAll(Pattern.quote(matcher.group()), Matcher.quoteReplacement(value));

            }
        }

        return (color) ? str(text) : text;
    }

    public enum EnumChat {
        PluginPrefix("&8[&aOblivion&6Chat&8]");

        private final String str;

        EnumChat(String str) {
            this.str = str;
        }

        public String getStr() {
            return this.str;
        }
    }

}

