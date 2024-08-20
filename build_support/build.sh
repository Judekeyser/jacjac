echo Building Example PKG

gcc -O3 -std=c17 -pedantic -Wall -Wextra -Werror -DJACJAC_IMPL -c c/src/jacjac.c -o c/target/jacjac.o
gcc -shared c/target/jacjac.o -o c/target/libjacjac
javac --module-source-path java/src --module clib -d java/target
export C_LIB_LOCATION=/tmp/symlink_root/c/target
java --enable-native-access=clib --module-path /tmp/symlink_root/java/target --add-modules clib build_support/JacJacExample.java