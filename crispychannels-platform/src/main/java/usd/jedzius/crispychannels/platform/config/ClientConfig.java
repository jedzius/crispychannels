package usd.jedzius.crispychannels.platform.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class ClientConfig extends OkaeriConfig {
    @Comment("Client ID")
    public int ID = 1;

    @Comment("TCP Server port")
    public int TCP = 21111;

    @Comment("UDP Server port")
    public int UDP = 21112;
}
