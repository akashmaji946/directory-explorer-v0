#!/bin/bash

# Define paths
SRC_DIR="src"
OUT_DIR="out/production/DirectoryExplorer"
JAR_NAME="directory.jar"
MANIFEST_FILE="manifest.txt"

echo ">>> Cleaning previous build..."
rm -rf "$OUT_DIR" "$JAR_NAME"

# Step 1: Compile Java files
echo ">>> Compiling Java source files..."
mkdir -p "$OUT_DIR"
javac -d "$OUT_DIR" "$SRC_DIR/me/akashmaji/directory/Main.java" "$SRC_DIR/me/akashmaji/directory/explorer/"*.java

# Step 2: Package into JAR
echo ">>> Creating JAR file..."
jar cfm "$JAR_NAME" "$MANIFEST_FILE" -C "$OUT_DIR" .

echo ">>> Build complete."
