class_d=bin
source_d=src
JFLAGS=-g -d $(class_d) -sourcepath $(source_d) -Xlint:all 
JAVAC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        MerkleTree.java \
        TestMerkleTreeClass.java \
	LogServer.java \
	TestLogServer.java \
	TestHash.java

MATCH='.*[^(_TEST)]\.java'

ifdef TEST
    ifeq ($(TEST), all)
        MATCH='.*_TEST\.java'
    else
        MATCH='.*\/$(TEST)\/.*_TEST\.java'
    endif
endif

S_FILES= $(shell find $(source_d) -regex $(MATCH))
C_FILES= $(patsubst $(source_d)/%.java, $(class_d)/%.class, $(S_FILES))

default: classes files

classes: $(class_d) 

files: $(C_FILES)

clean:
	$(RM) $(class_d)/*

$(class_d):
	mkdir $(class_d)

$(class_d)/%.class: $(source_d)/%.java
	@echo "JAVAC $<"
	@$(JAVAC) $(JFLAGS) $<
