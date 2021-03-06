=========================================================================
MATSim

Multi-Agent Transport Simulation

http://matsim.org/

Release Spring 2013  (internal number: 0.5.0)
=========================================================================


MATSim provides a framework to implement large-scale agent-based transport 
simulations. The framework consists of severel modules which can be combined 
or used stand-alone. Modules can be replaced by own implementations to test 
single aspects of your own work. Currently, MATSim offers a framework for 
demand-modeling, agent-based mobility-simulation (traffic flow simulation), 
re-planning, a controller to iteratively run simulations as well as methods 
to analyze the output generated by the modules.

-------------------------------------------------------------------------

USAGE:
- unzip the downloaded file
- java -jar matsim-0.5.0.jar
  this should show the release info
- java -cp matsim-0.5.0.jar org.matsim.run.Controler myconfig.xml
  this should run the simulation
- A Tutorial can be found online: http://matsim.org/docs/tutorials
  some example files are included in this release.
- If you want to develop code with MATSim, e.g. in Eclipse:
  add the matsim-jar to an Eclipse-Project.
  you should now be able to use the classes from matsim in your project.

-------------------------------------------------------------------------------
mrieser, 2013-03-11
