package com.clicker;


public class ConsoleParser {
    private static Player player = App.getPlayer();

    public static void parse(String input) {
        String[] command = input.split(" ");
        command[0] = command[0].substring(1);
        ParseCommands d = ParseCommands.valueOf(command[0].toUpperCase());
        switch (d) {
            case GIVE: {
                try {
                player.addClicks(Long.valueOf(command[1]));
                }
                catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                    break;
                }
                break;
            }
            case RESET: {
                App.reset();
                break;
            }
            default: {
                System.out.println("Invalid command");
            }
        }
        System.out.println(String.format("Command accepted: %s", d));
        }
    }

