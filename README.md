### Directory Explorer
This is a sample project made using Java and associated tools and libraries.
It will do the following:
- Take in a directory name `dir`
- Process the contents (files and directories) recursively
- Output the file names or file contents
- Refer to the API usage below (TODO: documentation in progress)

#### Steps to build and run [easy way]
- Setup
```bash
```bash
cd ~/Desktop
git clone <this repo url> DirectoryExplorer
cd ~/Desktop/DirectoryExplorer

# update the extensions.txt file with allowed extensions as you wish

mkdir -p out/production/DirectoryExplorer
```
- Build and Run
```bash
chmod +x *.sh

./build.sh

# use absolute or relative path for srcDir and destDir
./run.sh <srcDir> <destDir>

# delete the destDir manually or using:
rm -rf <desDir>
```

#### Steps to build and run [manual compile and run]
- Setup as: 
```bash
cd ~/Desktop
git clone <this repo url> DirectoryExplorer
cd ~/Desktop/DirectoryExplorer

# update the extensions.txt file with allowed extensions as you wish

mkdir -p out/production/DirectoryExplorer
```

- Build as: 
```bash
javac -d out/production/DirectoryExplorer src/me/akashmaji/directory/*.java src/me/akashmaji/directory/explorer/*.java
jar cfm directory.jar manifest.txt -C out/production/DirectoryExplorer .
```
- Run as:
```bash
# use absolute or relative path for srcDir and destDir
java -jar directory.jar <srcDir> <destDir>

# delete the destDir manually or using:
rm -rf <desDir>
```

##### Contact for Bugs and Issues:
- @Author: Akash Maji
- @Email: akashmaji@iisc.ac.in
- @Website: [akashmaj.me](https://www.akashmaj.me)
