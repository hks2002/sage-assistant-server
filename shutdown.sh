#!/usr/bin/bash

ps aux | grep sage-assistant-server.jar | head -n 1 | awk '{print $2}' | xargs kill -9
