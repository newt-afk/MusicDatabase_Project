# MusicDatabase_Project

## TODO
1. implement music using javafx media and mediaplayer
2. allow creation of playlists and albums in ui
3. allow setting volume
4. in UI, able to toggle music between linked and non-linked
5. send all potential errors to logger

## maybe?
1. make user able to set music directory, with default directory if not set
2. send files over internet

## files (not implemented)
* player setting file

# Contracts
* Linked music is identified by the first track in the link. 
* You are only allowed to create a new Music object if you are reading from file,
or the user made you do it. All other methods can only get music objects from a map stored in Helper.

## Potential problems
* I want to assume that all linked music start the same