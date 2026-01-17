# Hide Players On Map

A Hytale server plugin that hides player icons from the world map.

## Installation

1. Download `HidePlayersOnMap-x.x.x.jar` from [Releases](../../releases)
2. Place in your server's `plugins/` folder
3. Restart the server

## Building from Source

**Requirements:** Java 25, Maven 3.9+, [HytaleServer.jar](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual)

```bash
# Install HytaleServer.jar to local Maven repo (one-time setup)
mvn install:install-file -Dfile="path/to/HytaleServer.jar" \
    -DgroupId=com.hypixel.hytale \
    -DartifactId=HytaleServer-parent \
    -Dversion=1.0-SNAPSHOT \
    -Dpackaging=jar

# Build the plugin
mvn package
```

Output: `dist/HidePlayersOnMap-x.x.x.jar`

## License

MIT
