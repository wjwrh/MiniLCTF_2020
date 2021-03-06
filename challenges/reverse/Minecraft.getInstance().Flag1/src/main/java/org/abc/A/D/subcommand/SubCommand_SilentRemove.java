/*
 * This file is a part of project QuickShop, the name is SubCommand_SilentRemove.java
 * Copyright (C) Ghost_chu <https://github.com/Ghost-chu>
 * Copyright (C) Bukkit Commons Studio and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.abc.A.D.subcommand;

import java.util.ArrayList;
import java.util.List;

import org.abc.A.N.Shop;
import org.abc.A.util.MsgUtil;
import org.abc.A.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.abc.A.D.CommandProcesser;
import org.abc.A.QuickShop;

public class SubCommand_SilentRemove implements CommandProcesser {

    private final QuickShop plugin = QuickShop.instance;

    @Override
    public void onCommand(
        @NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] cmdArg) {
        if (cmdArg.length < 4) {
            Util.debugLog("Exception on command, cancel.");
            return;
        }

        final Player p = (Player) sender;
        final Shop shop =
            plugin
                .getShopManager()
                .getShop(
                    new Location(
                        plugin.getServer().getWorld(cmdArg[0]),
                        Integer.parseInt(cmdArg[1]),
                        Integer.parseInt(cmdArg[2]),
                        Integer.parseInt(cmdArg[3])));

        if (shop == null) {
            MsgUtil.sendMessage(sender,MsgUtil.getMessage("not-looking-at-shop", sender));
            return;
        }

        if (!shop.getModerator().isModerator(p.getUniqueId())
            && !QuickShop.getPermissionManager().hasPermission(sender, "quickshop.other.destroy")) {
            MsgUtil.sendMessage(sender,ChatColor.RED + MsgUtil.getMessage("no-permission", sender));
            return;
        }

        shop.onUnload();
        shop.delete();
    }

    @NotNull
    @Override
    public List<String> onTabComplete(
        @NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] cmdArg) {
        return new ArrayList<>();
    }

}
