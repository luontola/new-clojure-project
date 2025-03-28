# Bug in JEP 483: Ahead-of-Time Class Loading & Linking

When [the code](src/kata.clj) requires the `clojure.tools.logging` namespace, an "Archive heap points to a static field
that may hold a different value at runtime" error happens during the `-XX:AOTMode=create` step.

This bug is triggered by something in the `clojure.tools.logging` namespace, because requiring other namespaces doesn't
cause that error, and neither does requiring the namespaces that `clojure.tools.logging` itself requires.

```
❯ ./build.sh
+ java -version
openjdk version "24" 2025-03-18
OpenJDK Runtime Environment (build 24+36-3646)
OpenJDK 64-Bit Server VM (build 24+36-3646, mixed mode, sharing)
+ JAR_FILE=target/uberjar/kata.jar
+ lein uberjar
Compiling kata
Created /Users/esko/devel/new-clojure-project/target/uberjar/new-clojure-project-0.1.0-SNAPSHOT.jar
Created /Users/esko/devel/new-clojure-project/target/uberjar/kata.jar
+ java -XX:AOTMode=record -XX:AOTConfiguration=target/app.aotconf -jar target/uberjar/kata.jar
Mar 28, 2025 10:59:45 PM clojure.tools.logging$eval136$fn__139 invoke
INFO: Hello, World!
+ java -XX:AOTMode=create -XX:AOTConfiguration=target/app.aotconf -XX:AOTCache=target/app.aot --class-path target/uberjar/kata.jar
[0.496s][warning][cds] Skipping jdk/internal/event/Event: JFR event class
[0.687s][warning][cds,heap] Archive heap points to a static field that may hold a different value at runtime:
[0.687s][warning][cds,heap] Field: java/lang/invoke/MethodHandleImpl$BindCaller::CD_Object_array
[0.687s][warning][cds,heap] Value: jdk.internal.constant.ArrayClassDescImpl 
[0.687s][warning][cds,heap] {0x00000004001ebc28} - klass: 'jdk/internal/constant/ArrayClassDescImpl' - flags: 
[0.687s][warning][cds,heap] 
[0.687s][warning][cds,heap]  - ---- fields (total size 3 words):
[0.687s][warning][cds,heap]  - private final 'rank' 'I' @12  1 (0x00000001)
[0.687s][warning][cds,heap]  - private final 'elementType' 'Ljava/lang/constant/ClassDesc;' @16  a 'jdk/internal/constant/ClassOrInterfaceDescImpl'{0x0000000400105930} (0x80020b26)
[0.687s][warning][cds,heap]  - private 'cachedDescriptorString' 'Ljava/lang/String;' @20  "[Ljava/lang/Object;"{0x000000040030d808} (0x80061b01)
[0.687s][warning][cds,heap] --- trace begin ---
[0.687s][warning][cds,heap] [ 0] {0x000000040022e5a8} java.lang.Class (jdk.internal.constant.ConstantUtils::CD_Object_array)
[0.687s][warning][cds,heap] [ 1] {0x00000004001ebc28} jdk.internal.constant.ArrayClassDescImpl
[0.687s][warning][cds,heap] --- trace end ---
[0.687s][warning][cds,heap] 
[0.688s][error  ][cds,heap] Scanned 47008 objects. Found 1 case(s) where an object points to a static field that may hold a different value at runtime.
[0.688s][error  ][cds     ] An error has occurred while writing the shared archive file.
```
