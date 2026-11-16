#!/usr/bin/bash

./shutdown.sh
java -cp "sage-assistant-server.jar" com.da.sage.assistant.VertxApp --conf=config-prod.json --options=vertx-options.json
