#include "Pictures.h"
#include <avr/pgmspace.h>

unsigned char picture_data[8][3][8][8] PROGMEM = {
	/* RD1 */ {
		/* RED */ {
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xDD, 0xFF, 0x11, 0x00, 0x00, 0x00, 0x00 },
			{ 0xDD, 0xAA, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x77, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11 },
			{ 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE },
			{ 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE, 0xFF },
		},
		/* GREEN */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x44, 0xBB },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xDD },
		},
		/* BLUE */ {
			{ 0x00, 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0xAA, 0x33, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00, 0x00 },
		},
	},
	/* RD2 */ {
		/* RED */ {
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
		},
		/* GREEN */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
		/* BLUE */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
	},
	/* RD3 */ {
		/* RED */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
		/* GREEN */ {
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
		},
		/* BLUE */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
	},
	/* RD4 */ {
		/* RED */ {
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xDD, 0xFF, 0x11, 0x00, 0x00, 0x00, 0x00 },
			{ 0xDD, 0xAA, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x77, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11 },
			{ 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE },
			{ 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE, 0xFF },
		},
		/* GREEN */ {
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x44, 0xBB },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x44, 0xBB, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xDD },
		},
		/* BLUE */ {
			{ 0x00, 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0xAA, 0x33, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00, 0x00 },
		},
	},
	/* RD4 */ {
		/* RED */ {
			{ 0xFF, 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xDD, 0x77, 0x11, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xDD, 0xFF, 0x11, 0x00, 0x00, 0x00, 0x00 },
			{ 0xDD, 0xAA, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x77, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11 },
			{ 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE },
			{ 0x00, 0x00, 0x00, 0x00, 0x11, 0x77, 0xEE, 0xFF },
		},
		/* GREEN */ {
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x44, 0xBB },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x44, 0xBB, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x44, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xBB, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xDD },
		},
		/* BLUE */ {
			{ 0x00, 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x66, 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0xEE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0xAA, 0x33, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xAA, 0x33, 0x00, 0x00, 0x00, 0x00 },
		},
	},
	/* TEST_PATTERN */ {
		/* RED */ {
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40 },
			{ 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x80 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x80, 0xC0 },
			{ 0x00, 0x00, 0x00, 0x00, 0x40, 0x80, 0xC0, 0xFF },
		},
		/* GREEN */ {
			{ 0x00, 0x40, 0x80, 0xC0, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x40, 0x80, 0xC0, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x80, 0xC0, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x80 },
			{ 0xC0, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x80, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
		/* BLUE */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x80, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x80, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x80, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x80, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
			{ 0x80, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
		},
	},
	/* GAMMA_TEST */ {
		/* RED */ {
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
		},
		/* GREEN */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
		},
		/* BLUE */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
			{ 0xFF, 0xEE, 0xDD, 0xCC, 0xBB, 0xAA, 0x99, 0x88 },
			{ 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 },
		},
	},
	/* RGB */ {
		/* RED */ {
			{ 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x00 },
			{ 0x00, 0xFF, 0xFF, 0x00, 0x00, 0xFF, 0xFF, 0x00 },
			{ 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF },
			{ 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0xFF, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00 },
		},
		/* GREEN */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x00 },
			{ 0x00, 0xFF, 0xFF, 0x00, 0x00, 0xFF, 0xFF, 0x00 },
			{ 0x00, 0xFF, 0x00, 0x00, 0x00, 0x00, 0xFF, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0xFF, 0x00, 0xFF, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x00 },
		},
		/* BLUE */ {
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0xFF, 0x00, 0x00, 0xFF, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00 },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF },
			{ 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00 },
		},
	},
};

unsigned char get_picture_color(unsigned char picture, unsigned char channel, unsigned char row, unsigned char column) {
	return pgm_read_byte(&(picture_data[picture % 8][channel % 3][row & 0x7][column & 0x7]));
};
