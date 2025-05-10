#!/bin/bash

# Check if two arguments are passed
if [ "$#" -ne 2 ]; then
    echo "Usage: ./run.sh <src_directory> <dest_directory>"
    exit 1
fi

SRC="$1"
DEST="$2"
JAR_NAME="directory.jar"

# Run the JAR file with arguments
echo ">>> Running directory explorer..."
java -jar "$JAR_NAME" "$SRC" "$DEST"
