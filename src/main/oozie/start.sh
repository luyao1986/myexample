hadoop fs -rm -r  /user/shiyao/workflows/allmyexample/*
hadoop fs -copyFromLocal * /user/shiyao/workflows/allmyexample/
/home/y/var/yoozieclient/bin/oozie job -run -auth KERBEROS -config job.properties -oozie http://mithrilred-oozie.red.ygrid.yahoo.com:4080/oozie/