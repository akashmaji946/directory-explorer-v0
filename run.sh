#!/bin/bash

# Check if two arguments are passed
if [ "$#" -ne 3 ]; then
    echo "Usage: ./run.sh <src_directory> <enc_directory> <dec_directory>"
    exit 1
fi

SRC="$1"
ENC="$2"
DEC="$3"
JAR_NAME="directory.jar"

# Run the JAR file with arguments
echo ">>> Running directory explorer..."
java -jar "$JAR_NAME" "$SRC" "$ENC" "$DEC"
