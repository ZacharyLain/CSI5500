obj-m += hello.o

KDIR := /lib/modules/$(shell uname -r)/build
PWD := $(shell pwd)

CC = gcc-12

all:
	$(MAKE) -C $(KDIR) M=$(PWD) CC=$(CC) modules

clean:
	$(MAKE) -C $(KDIR) M=$(PWD) clean
