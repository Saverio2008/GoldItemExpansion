#!/bin/bash

TARGET_DIR="./build/dist"
mkdir -p "$TARGET_DIR"

for PLATFORM in fabric neoforge; do
  BUILD_DIR="./$PLATFORM/build/libs"
  if [ -d "$BUILD_DIR" ]; then
    find "$BUILD_DIR" -type f -name "*-mc*-$PLATFORM.jar" | while IFS= read -r jarfile; do
      filename=$(basename "$jarfile")
      destination="$TARGET_DIR/$filename"

      [ -e "$destination" ] && rm "$destination"
      mv "$jarfile" "$destination"
    done
  fi
done