#!/bin/bash
cd "$(dirname "$0")"

NPROC='threads='$(nproc)
sed -i "s/threads=.*/${NPROC}/" conf/app.conf

java -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar ./lib/aibench-aibench-${aibench.version}.jar
