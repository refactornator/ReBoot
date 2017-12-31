#!/usr/bin/env bash

lsof -i :3000 | grep node | awk '{ print $2 }' | xargs kill -9
