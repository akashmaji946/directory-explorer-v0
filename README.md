### Directory Explorer
This is a sample project made using Java and associated tools and libraries.
It will do the following:
- Take in a directory name `dir`
- Process the contents (files and directories) recursively
- Output the file names or file contents
- Refer to the API usage below (TODO: documentation in progress)

#### Steps to build and run
- Setup as: 
```bash
cd ~/Desktop
git clone <this repo url> DirectoryExplorer
cd ~/Desktop/DirectoryExplorer

mkdir -p out/production/DirectoryExplorer
```

- Build as: 
```bash
javac -d out/production/DirectoryExplorer src/me/akashmaji/directory/*.java src/me/akashmaji/directory/explorer/*.java
jar cfm directory.jar manifest.txt -C out/production/DirectoryExplorer .
```
- Run as:
```bash
java -jar directory.jar <directory name (relative or absolute path)>
```

##### Contact for Bugs and Issues:
- @Author: Akash Maji
- @Email: akashmaji@iisc.ac.in
- @Website: akashmaj.me
