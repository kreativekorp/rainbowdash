SRCFILES = src/com/kreative/*/*/*.java src/com/kreative/*/*/*/*.java

all: clean bin osxclean RainbowStudio.jar RainbowStudio.app RainbowStudio.exe RainbowStudio-Win32 RainbowStudio-Linux32 RainbowStudio-Linux64 RainbowStudio-MacOSX.zip RainbowStudio-Win32.zip RainbowStudio-Linux32.zip RainbowStudio-Linux64.zip

eclipseall: eclipseclean osxclean RainbowStudio.jar RainbowStudio.app RainbowStudio.exe RainbowStudio-Win32 RainbowStudio-Linux32 RainbowStudio-Linux64 RainbowStudio-MacOSX.zip RainbowStudio-Win32.zip RainbowStudio-Linux32.zip RainbowStudio-Linux64.zip

clean:
	rm -rf bin
	rm -rf RainbowStudio.jar
	rm -rf RainbowStudio.app
	rm -rf RainbowStudio.exe
	rm -rf RainbowStudio-Win32
	rm -rf RainbowStudio-Linux32
	rm -rf RainbowStudio-Linux64
	rm -rf RainbowStudio*.zip

eclipseclean:
	rm -rf RainbowStudio.jar
	rm -rf RainbowStudio.app
	rm -rf RainbowStudio.exe
	rm -rf RainbowStudio-Win32
	rm -rf RainbowStudio-Linux32
	rm -rf RainbowStudio-Linux64
	rm -rf RainbowStudio*.zip

bin:
	mkdir -p bin
	javac -target 1.5 -sourcepath src $(SRCFILES) -d bin
	cp src/com/kreative/rainbowstudio/firmware/*.hex bin/com/kreative/rainbowstudio/firmware/
	cp src/com/kreative/rainbowstudio/resources/*.png bin/com/kreative/rainbowstudio/resources/

osxclean:
	export COPYFILE_DISABLE=true
	rm -f src/.DS_Store
	rm -f src/*/.DS_Store
	rm -f src/*/*/.DS_Store
	rm -f src/*/*/*/.DS_Store
	rm -f src/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/*/*/.DS_Store
	rm -f bin/.DS_Store
	rm -f bin/*/.DS_Store
	rm -f bin/*/*/.DS_Store
	rm -f bin/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/*/*/.DS_Store

RainbowStudio.jar: osxclean
	jar cmf dep/MANIFEST.MF RainbowStudio.jar -C bin com/kreative/rainbowstudio

RainbowStudio.app: RainbowStudio.jar
	mkdir -p RainbowStudio.app/Contents/MacOS
	mkdir -p RainbowStudio.app/Contents/Resources/Java
	mkdir -p RainbowStudio.app/Contents/Resources/Examples
	cp dep/JavaApplicationStub RainbowStudio.app/Contents/MacOS/RainbowStudio
	cp RainbowStudio.jar RainbowStudio.app/Contents/Resources/Java
	cp dep/rxtx/*.jar RainbowStudio.app/Contents/Resources/Java
	cp dep/rxtx/*.jnilib RainbowStudio.app/Contents/Resources/Java
	cp dep/avr-mac/* RainbowStudio.app/Contents/Resources
	cp dep/icons/*.icns RainbowStudio.app/Contents/Resources
	cp dep/Info.plist RainbowStudio.app/Contents
	cp dep/PkgInfo RainbowStudio.app/Contents
	cp rbd/*.rbd RainbowStudio.app/Contents/Resources/Examples/

RainbowStudio.exe: RainbowStudio.jar
ifeq ($(LAUNCH4J_HOME),)
	echo 'Please set $$LAUNCH4J_HOME environment variable to compile exe.'
else
	cd "$(LAUNCH4J_HOME)" ; java -Dlaunch4j.tmpdir=/tmp/ -jar "$(LAUNCH4J_HOME)/launch4j.jar" "$(PWD)/dep/RainbowStudio.xml"
endif

RainbowStudio-Win32: RainbowStudio.exe
	mkdir -p RainbowStudio-Win32/Examples
	cp RainbowStudio.exe RainbowStudio-Win32
	cp RainbowStudio.jar RainbowStudio-Win32
	cp dep/rxtx/*.jar RainbowStudio-Win32
	cp dep/rxtx/*.dll RainbowStudio-Win32
	cp dep/avr-win/* RainbowStudio-Win32
	cp dep/icons/*.ico RainbowStudio-Win32
	cp rbd/*.rbd RainbowStudio-Win32/Examples/

RainbowStudio-Linux32: RainbowStudio.jar
	mkdir -p RainbowStudio-Linux32/Examples
	cp RainbowStudio.jar RainbowStudio-Linux32
	cp dep/rxtx/*.jar RainbowStudio-Linux32
	cp dep/rxtx/*.so RainbowStudio-Linux32
	cp dep/avr-lin/* RainbowStudio-Linux32
	cp dep/icons/*.png RainbowStudio-Linux32
	cp dep/icons/*.svg RainbowStudio-Linux32
	cp rbd/*.rbd RainbowStudio-Linux32/Examples/

RainbowStudio-Linux64: RainbowStudio.jar
	mkdir -p RainbowStudio-Linux64/Examples
	cp RainbowStudio.jar RainbowStudio-Linux64
	cp dep/rxtx/*.jar RainbowStudio-Linux64
	cp dep/rxtx/*.so RainbowStudio-Linux64
	cp dep/avr-lin/* RainbowStudio-Linux64
	cp dep/icons/*.png RainbowStudio-Linux64
	cp dep/icons/*.svg RainbowStudio-Linux64
	cp RainbowStudio-Linux64/librxtxSerial64.so RainbowStudio-Linux64/librxtxSerial.so
	cp RainbowStudio-Linux64/avrdude64 RainbowStudio-Linux64/avrdude
	cp rbd/*.rbd RainbowStudio-Linux64/Examples/

RainbowStudio-MacOSX.zip: RainbowStudio.app
	zip -r RainbowStudio-MacOSX.zip RainbowStudio.app

RainbowStudio-Win32.zip: RainbowStudio-Win32
	zip -r RainbowStudio-Win32.zip RainbowStudio-Win32

RainbowStudio-Linux32.zip: RainbowStudio-Linux32
	zip -r RainbowStudio-Linux32.zip RainbowStudio-Linux32

RainbowStudio-Linux64.zip: RainbowStudio-Linux64
	zip -r RainbowStudio-Linux64.zip RainbowStudio-Linux64

.PHONY: all eclipseall clean eclipseclean osxclean
