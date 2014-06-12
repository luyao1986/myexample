echo "start to prepare data for oozie"
pwd=`pwd`
echo "pwd = $pwd"
rm src/main/oozie/lib
mkdir src/main/oozie/lib
cp target/*-jar-with-dependencies.jar src/main/oozie/lib/
echo "done cp the target/*-jar-with-dependencies.jar  to src/main/oozie/lib/"
