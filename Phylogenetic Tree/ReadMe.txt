Given a series of DNA sequences this program constructs a corresponding phylogenetic tree.
The tree is then displayed in manner similar to an actual tree For example, given the input:

ACTC
AC
ACC
TAC
ACCD
TCA
T
done

The result phylogenetic tree would look like:

*
|-AC
| |-ACC
| | |-ACCD
| |-ACTC
|-T
| |-TAC
| |-TCA
