# tasks

A Spring Boot application, that realise two web services:

(1) http://localhost:8080/tasks/order 

with body, for example:

  { "tasks" :[ { "name" : "task-1" , "command" : "touch /tmp/file1" }, { "name" : "task-2" , "command" : "cat /tmp/file1" , "requires" :[ "task-3" ] }, { "name" : "task-3" , "command" : "echo 'Hello World!' > /tmp/file1" , "requires" :[ "task-1" ] }, { "name" : "task-4" , "command" : "rm /tmp/file1" , "requires" :[ "task-2" , "task-3" ] } ] } 

returns tasks in their proper execution order, for example:
  [ { "name" : "task-1" , "command" : "touch /tmp/file1" }, { "name" : "task-3" , "command" : "echo 'Hello World!' > /tmp/file1" }, { "name" : "task-2" , "command" : "cat /tmp/file1" }, { "name" : "task-4" , "command" : "rm /tmp/file1" } ]
  
(2) http://localhost:8080/tasks/script 
with body, for example:

  { "tasks" :[ { "name" : "task-1" , "command" : "touch /tmp/file1" }, { "name" : "task-2" , "command" : "cat /tmp/file1" , "requires" :[ "task-3" ] }, { "name" : "task-3" , "command" : "echo 'Hello World!' > /tmp/file1" , "requires" :[ "task-1" ] }, { "name" : "task-4" , "command" : "rm /tmp/file1" , "requires" :[ "task-2" , "task-3" ] } ] } 

orders tasks in their proper execution order and returns the execution bash script, for example:
  #!/usr/bin/env bash 
  
  touch /tmp/file1 
  echo "Hello World!" > /tmp/file1 
  cat /tmp/file1 
  rm /tmp/file1
