all: becho aecho rtime clocktest rainbowd rainbowclock rainbowmarquee rainbowspectrum

clean:
	rm -f becho aecho rtime clocktest rainbowd rainbowclock rainbowmarquee rainbowspectrum
	rm -f *.class

becho: becho.c
	gcc -O3 -Wall -ansi -pedantic -o becho becho.c

aecho: aecho.c
	gcc -O3 -Wall -ansi -pedantic -o aecho aecho.c

rtime: rtime.c
	gcc -O3 -Wall -ansi -pedantic -o rtime rclock.c rtime.c

rtime.class: rtime.java
	javac rtime.java

clocktest: clocktest.c
	gcc -O3 -Wall -ansi -pedantic -o clocktest clocktest.c

rainbowd: rainbowd.c
	gcc -O3 -Wall -ansi -pedantic -o rainbowd rainbowd.c

rainbowclock: rainbowclock.c
	gcc -O3 -Wall -ansi -pedantic -o rainbowclock rainbowclock.c

rainbowmarquee: rainbowmarquee.c
	gcc -O3 -Wall -ansi -pedantic -o rainbowmarquee rclock.c rainbowmarquee.c

rainbowspectrum: rainbowspectrum.c
	gcc -O3 -Wall -ansi -pedantic -o rainbowspectrum rainbowspectrum.c

.PHONY: all clean
