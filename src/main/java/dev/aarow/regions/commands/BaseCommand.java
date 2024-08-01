package dev.aarow.regions.commands;

import dev.aarow.regions.Regions;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.chat.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    private final CommandInfo commandInfo;

    public Regions regions = Regions.getInstance();
    public RegionsPlugin plugin = RegionsPlugin.getInstance();


    public BaseCommand(){
        this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);

        plugin.getCommand(commandInfo.name()).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandInfo.permission().isEmpty() && !(commandSender.hasPermission(commandInfo.permission()))){
            commandSender.sendMessage(CC.translate("&cNo permission."));
            return true;
        }

        if(commandInfo.playerOnly()){
            if(!(commandSender instanceof Player)){
                commandSender.sendMessage(CC.translate("&cOnly players may execute this command."));
                return true;
            }

            this.execute((Player) commandSender, strings);
            return true;
        }

        this.execute(commandSender, strings);
        return true;
    }

    public void execute(Player player, String args[]){}
    public void execute(CommandSender commandSender, String args[]){}
}
