package nl.craftsmp.essentials.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

@SuppressWarnings("ALL")
public class ChatUtils {

    private static final MiniMessage miniMessage = MiniMessage.builder()
            .editTags(tags -> {
                tags.resolver(TagResolver.resolver("primary", Tag.styling(TextColor.fromHexString("#43FF61"))));
                tags.resolver(TagResolver.resolver("secondary", Tag.styling(TextColor.fromHexString("#35bd62"))));
                tags.resolver(TagResolver.resolver("danger", Tag.styling(TextColor.fromHexString("#ff999b"))));
            })
            .build();

    public static Component color(String message) {
        return miniMessage.deserialize(message);
    }

    public static Component color(String message, TagResolver... resolvers) {
        return miniMessage.deserialize(message, resolvers);
    }

}
