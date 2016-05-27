JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        MerkleTree.java \
        TestMerkleTreeClass.java \
		LogServer.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
