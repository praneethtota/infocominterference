
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;



 


class edge {
	int src = 0;
	int dst = 0;
	int cap = 0;
	int delay = 0;
	double flow = 0;
	int t = 0;
	int id;
	
	double price = 0;
	double epsilon = .5;
	double delta = 2;
	
	boolean eneredge=false;
	boolean bufedge=false;
	boolean normedge=false;
	double priceOnOrig = 0;
	double price1OnOrig = 0;
	double price2OnOrig = 0;
	double price3OnOrig = 0;

	double FlowOnOrig = 0;
	double zet=0;
	Vector<path> pathsUsingEdge = new Vector<path>();

	public edge(int sr, int des, int cp, int ti, int idd, boolean ener, boolean buf, boolean norm, double ze) {
		src = sr;
		dst = des;
		cap = cp;
		t = ti;
		id = idd;
		eneredge=ener;
		bufedge=buf;
		normedge=norm;
		zet=ze;


	}





	double FlowOnEdge() {
		double flow = 0;
		for (path element : pathsUsingEdge) {
			flow = flow + element.flow;

		}
		return flow;
	}

	double priceOnOrigEdge() {
		
		double p1=(FlowOnOrig - zet*cap + epsilon);
		//System.out.println("p1="+p1);
		price = p1 / (Math.pow(epsilon, delta));
		//System.out.println("price="+ price);
		//	System.out.println("In edge="+price+"\t FlowonOrig="+FlowOnOrig+"\t Cap="+cap);
		if (price < 0)
			price = 0;
		return price;
	}


	double price1OnOrigEdge() {
		
		double p1=(FlowOnOrig - cap + epsilon);
		//System.out.println("p1="+p1);
		price = p1 / (Math.pow(epsilon, delta));
		//System.out.println("price="+ price);
		//	System.out.println("In edge="+price+"\t FlowonOrig="+FlowOnOrig+"\t Cap="+cap);
		if (price < 0)
			price = 0;
		return price;
	}



	double price2OnOrigEdge() {
		
		double p1=(zet - 1 + epsilon);
		//System.out.println("p1="+p1);
		price = p1 / (Math.pow(epsilon, delta));
		//System.out.println("price="+ price);
		//	System.out.println("In edge="+price+"\t FlowonOrig="+FlowOnOrig+"\t Cap="+cap);
		if (price < 0)
			price = 0;
		return price;
	}



	double price3OnOrigEdge() {
		double p1=(FlowOnOrig - cap + epsilon);
		//System.out.println("p1="+p1);
		price = p1 / (Math.pow(epsilon, delta));
		//System.out.println("price="+ price);
		//	System.out.println("In edge="+price+"\t FlowonOrig="+FlowOnOrig+"\t Cap="+cap);
		if (price < 0)
			price = 0;
		return price;
	}



}

class path {
	double flow = 0;
	double zet=0;
	Vector<edge> seq = new Vector<edge>();
	int source;
	int dest;

	public static path convertSPathtofpath(ArrayList PathfromBF, graph G) {
		path p = new path();
		if(PathfromBF!=null)
			for (int i = 0; i < PathfromBF.size() - 1; i++) {
				int src = (Integer) PathfromBF.get(i);
				int dest = (Integer) PathfromBF.get(i + 1);

				p.seq.add(G.tGraph[src][dest]);
			}
		return p;

	}

	public static boolean pathcheck(Vector<path> fpath,path npath){
		//return false if path not found

		boolean found1=true;
		for (int i = 0; i < fpath.size(); i++) {
			path ipath = fpath.get(i);
			if (ipath.seq.size() == npath.seq.size())
				for (int j = 0; j < ipath.seq.size(); j++) {
					if (ipath.seq.get(j).id != npath.seq.get(j).id)
						found1 = false;
					break;
				}

		}
		return found1;

	}
}

class flowC {

	Vector<path> fpath;
	double req = 0;
	int src;
	int dest;
	double totalflow;
    double totalzet;
	
	public flowC(int r, int sc, int des) {
		totalflow = 0;
		src = sc;
		dest = des;
		req = r;
		fpath = new Vector<path>();
		totalzet=0;
	}

	double flowValue() {
		double fval = 0;
		if (fpath.size() > 0) {
			for (int i = 0; i < fpath.size(); i++) {
				fval = fval + fpath.get(i).flow;
			}
		}
		return fval;
	}

	int addPath(path npath) {
		path ipath = new path();
		int pathId = fpath.size();
		// edge e5;
		boolean found = false;
		boolean found1 = true;
		for (int i = 0; i < fpath.size(); i++) {
			// if (fpath.get(i).equals(npath))
			ipath = fpath.get(i);
			if (ipath.seq.size() == npath.seq.size())
				for (int j = 0; j < ipath.seq.size(); j++) {
					if (ipath.seq.get(j).id != npath.seq.get(j).id)
						found1 = false;
				}
			if (found1 == true) {
				found = true;
				pathId = i;
				// System.out.println("pathid==" + pathId);
			}
		}
		if (!found)
			fpath.add(npath);
		//	System.out.println("pathid="+pathId);

		return pathId;
	}

	double UpdateFlowOnPath(int id) throws IOException {
		double kappa = 0.1;
		double priceOfPath = 0.0;		
		path p = fpath.get(id);
		// findprice of path
		for (edge e : p.seq) {

			if(e.normedge==true)
				priceOfPath = priceOfPath + e.priceOnOrig + e.price3OnOrig ;
			if(e.bufedge==true)
				priceOfPath = priceOfPath + e.price3OnOrig;
			if(e.eneredge==true)
				priceOfPath = priceOfPath + e.price1OnOrig;



			//	System.out.println("priceOfPath = "+priceOfPath + "e.priceOnOrig="+e.priceOnOrig);
		}
		double fl1=(1.0 - priceOfPath);
		double flowUp = kappa * fl1;
		//	System.out.println("actual flowup ="+ flowUp );
		if (flowUp < 0)
			flowUp = 0;
		totalflow = totalflow + flowUp;
		//System.out.println("totalflow ="+ totalflow );
		if(totalflow<0)
			totalflow=0;


		p.flow = p.flow + flowUp;

		for (edge e : p.seq) {
			e.flow = e.flow + flowUp;
			//	System.out.println("flow on e"+e.flow);
			if(e.flow<=e.cap)
			{
				//System.out.println("okay");
			}
			else
			{
				System.out.println("error, exiting");
				System.exit(0);
			}
			if(e.pathsUsingEdge.size()!=0)
			{if(!path.pathcheck(e.pathsUsingEdge,p)){
				//	System.out.println("Added path");
				e.pathsUsingEdge.add(p);
			}}
			else{
				e.pathsUsingEdge.add(p);
			}

		}
		//System.out.println("flowup ="+ flowUp );
		return flowUp;
	}


	double UpdatezetOnPath(int id){
		double kappahat = 0.1;
		double priceOfPath = 0.0;		
		path p = fpath.get(id);
		// findprice of path
		for (edge e : p.seq) {

			if(e.normedge==true)

				priceOfPath = priceOfPath + e.cap*e.priceOnOrig - e.price2OnOrig;


			//	System.out.println("priceOfPath = "+priceOfPath + "e.price2OnOrig="+e.price2OnOrig);
		}

		double zetUp = kappahat * priceOfPath;
		//	System.out.println("actual flowup ="+ flowUp );
		if (zetUp < 0)
			zetUp = 0;
		totalzet = totalzet + zetUp;
		//System.out.println("totalflow ="+ totalflow );
		if(totalzet<0)
			totalzet=0;


		p.zet = p.zet + zetUp;

		for (edge e : p.seq) {
			e.zet = e.zet + zetUp;
			//	System.out.println("flow on e"+e.flow);
			if(e.pathsUsingEdge.size()!=0)
			{if(!path.pathcheck(e.pathsUsingEdge,p)){
				//	System.out.println("Added path");
				e.pathsUsingEdge.add(p);
			}}
			else{
				e.pathsUsingEdge.add(p);
			}

		}
		//System.out.println("flowup ="+ flowUp );
		return zetUp;
	}



} 








class beta{
	String e1;
	String e2;
	int t1;
	int t2;
	double val;

	public beta(String e11, String e12, int t11, int t12, double value){
		e1=e11;
		e2=e12;
		t1=t11;
		t2=t12;
		val=value;

	}
}

class graph {
	int NumCopies = 0;
	int NumVert;
	int NumEdges = 0;
	edge[][] tGraph = new edge[4500][4500]; 
	edge[][] Ograph = new edge[150][150]; 
	beta [] bgraph=new beta[500000];
	static int bufmax=10;
	static int energymax=4;

	public graph(String filename, int T) throws IOException {
		NumCopies = T;
		String tmp;
		int a, b, c;
		String[] res = null;
		boolean ener=false;
		boolean buf=false;
		boolean norm=false;
		double ze=.1;

		// tGraph = null; //change
		FileReader input1 = new FileReader(filename);
		BufferedReader bufRead1 = new BufferedReader(input1);
		tmp = bufRead1.readLine();
		int e = 0;
		res = tmp.split("\\s");
		a = Integer.parseInt(res[0]);
		b = Integer.parseInt(res[1]);
		if (tmp != null) {
			NumVert = a;
			NumEdges = b;
		}
		;
		tmp = bufRead1.readLine();
		while (tmp != null) {
			if (tmp != null) {

				res = tmp.split("\\s");
				a = Integer.parseInt(res[0]);
				b = Integer.parseInt(res[1]);
				c = Integer.parseInt(res[2]);
				if(a==b){buf=true;}
				else {
					if((a+1 == b) && (Math.abs(a%2) == 0))
						ener=true;
					else if(Math.abs(a%2) == 1)
						norm=true;
				}

				edge edges1 = new edge(a, b, c, -1, e, ener, buf, norm, ze );
				Ograph[a][b] = edges1;

				int delay = 60/c;

				for (int i = 0; i < T; i++) {

					boolean bufferedge=false;
					boolean energyedge= false;
					boolean normaledge= false;
					if(a==b){bufferedge=true;}
					else {
						if((a+1 == b) && (Math.abs(a%2) == 0))
							energyedge=true;
						else if(Math.abs(a%2) == 1)
							normaledge=true;
					}

					if (bufferedge && i < T -  1) {

						//	System.out.println(c);
						edge edges = new edge(a * T + i, b * T + i + 1, c, i, e, false, true, false, ze);
						tGraph[a * T + i][b * T + i + 1] = edges;

					} else//Consider energy edges */
						if (energyedge) {
							edge edges = new edge(a * T + i, b * T + i , c, i, e, true, false, false, ze);
							tGraph[a * T + i][b * T + i] = edges;
						} else 
							if(normaledge && delay+i < T){
								//c=60;
								edge edges = new edge(a * T + i, b * T + i + (60 / c),
										c, i, e, false, false, true, ze);
								tGraph[a * T + i][b * T + i + (60 / c)] = edges;
							}
					e++;
				}
				// to create vertex edges

			}

			tmp = bufRead1.readLine();

		}
		bufRead1.close();
	}

	void PriceEdges() {

		for (int i = 0; i < NumVert; i++)
			for (int j = 0; j < NumVert; j++) {
				if (Ograph[i][j] != null) {// edge e=new edge;
					double flow;
					flow = 0;
					boolean bufferedge=false;
					boolean energyedge= false;
					boolean normaledge= false;
					if(i==j){bufferedge=true;}
					else {
						if((i+1 == j) && (Math.abs(i%2) == 0))
							energyedge=true;
						else normaledge=true;
					}
					int delay=60 / Ograph[i][j].cap;
					for (int t = 0; t < NumCopies; t++) {
						if (bufferedge && t < NumCopies - 1)
						{

							flow = flow
									+ tGraph[i * NumCopies + t][j * NumCopies
									                            + t + 1].FlowOnEdge();}
						else
							if (energyedge){
								flow = flow
										+ tGraph[i * NumCopies + t][j * NumCopies
										                            + t].FlowOnEdge();
							}else
								if(normaledge && delay+t < NumCopies){
									flow = flow
											+ tGraph[i * NumCopies + t][j * NumCopies
											                            + t + (60 / Ograph[i][j].cap)]
											                            		.FlowOnEdge();
								}


					}
					Ograph[i][j].flow = flow;
					// Ograph[i][i].flow=flow1;
					Ograph[i][j].FlowOnOrig = flow;
					// Ograph[i][i].FlowOnOrig=flow1;


					if(Ograph[i][j].eneredge==true)
						Ograph[i][j].price1OnOrig = Ograph[i][j].price1OnOrigEdge();

					if(Ograph[i][j].normedge==true)
					{
						Ograph[i][j].priceOnOrig = Ograph[i][j].priceOnOrigEdge();
						Ograph[i][j].price2OnOrig = 0;
						int de=Ograph[i][j].t;
						int add=0;
						//for(int k=0; k<=NumVert*NumVert+NumVert; k++)
						//for (int t = 0; t < NumCopies; t++)
						//for(int t1=t; t1<bgraph=t+de; t1++ )
						for(int k=0; k<=10000; k++)	{
						    if(bgraph[k]!=null)	{
							String a=bgraph[k].e1;
							String b=bgraph[k].e2;
							String[] res1 = a.split(",");
							int c = Integer.parseInt(res1[0]);
							int	d = Integer.parseInt(res1[1]);
							res1 = b.split(",");
							int e = Integer.parseInt(res1[0]);
							int	f = Integer.parseInt(res1[1]);

							if(c==i && d==j)
							{
								bgraph[k].val=Ograph[i][j].price2OnOrigEdge();
								Ograph[i][j].price2OnOrig = Ograph[i][j].price2OnOrig+ bgraph[k].val;
							}
							if(e==i && f==j)
							{
								bgraph[k].val=Ograph[i][j].price2OnOrigEdge();
								Ograph[i][j].price2OnOrig = Ograph[i][j].price2OnOrig+ bgraph[k].val;
						
							}
						    }}
						if((Ograph[i][j].normedge==true)||(Ograph[i][j].bufedge==true))
							Ograph[i][j].price3OnOrig = Ograph[i][j].price3OnOrigEdge();


						//System.out.println("ograph ="+Ograph[i][j].priceOnOrigEdge());
						// Ograph[i][i].priceOnOrig=Ograph[i][i].priceOnOrigEdge();
					}
				}
			}
		for (int i = 0; i < NumVert; i++)
			for (int j = 0; j < NumVert; j++) {
				if (Ograph[i][j] != null) {
					//int ener0=Math.abs(i % 4);
					//int ener1=Math.abs(j % 4);
					boolean bufferedge=false;
					boolean energyedge= false;
					boolean normaledge= false;
					if(i==j){bufferedge=true;}
					else {
						if((i+1 == j) && (Math.abs(i%2) == 0))
							energyedge=true;
						else normaledge=true;
						//}
						int delay= 60 / Ograph[i][j].cap;
						for (int t = 0; t < NumCopies; t++) {
							if (bufferedge && t < NumCopies-1) {
								tGraph[i * NumCopies + t][j * NumCopies + t + 1].FlowOnOrig = Ograph[i][j].FlowOnOrig;
								// tGraph[i*t][i*t+1].FlowOnOrig=Ograph[i][i].FlowOnOrig;

								tGraph[i * NumCopies + t][j * NumCopies + t + 1].price3OnOrig = Ograph[i][j].price3OnOrig;
							} else 
								if  (energyedge) {
									tGraph[i * NumCopies + t][j * NumCopies + t ].FlowOnOrig = Ograph[i][j].FlowOnOrig;
									//		System.out.println("energeyprice="+ Ograph[i][j].priceOnOrig+"\t Flow="+Ograph[i][j].FlowOnOrig);
									// tGraph[i*t][i*t+1].FlowOnOrig=Ograph[i][i].FlowOnOrig;
									tGraph[i * NumCopies + t][j * NumCopies + t ].price1OnOrig = Ograph[i][j].price1OnOrig;
								} else if(normaledge && delay+t < NumCopies){
									tGraph[i * NumCopies + t][j * NumCopies + t
									                          + (60 / Ograph[i][j].cap)].FlowOnOrig = Ograph[i][j].FlowOnOrig;
									// tGraph[i*t][i*t+1].FlowOnOrig=Ograph[i][i].FlowOnOrig;
									tGraph[i * NumCopies + t][j * NumCopies + t
									                          + (60 / Ograph[i][j].cap)].priceOnOrig = Ograph[i][j].priceOnOrig;
									tGraph[i * NumCopies + t][j * NumCopies + t
									                          + (60 / Ograph[i][j].cap)].price2OnOrig = Ograph[i][j].price2OnOrig;
									tGraph[i * NumCopies + t][j * NumCopies + t
									                          + (60 / Ograph[i][j].cap)].price3OnOrig = Ograph[i][j].price3OnOrig;
									// tGraph[i*t][i*t+1].priceOnOrig=Ograph[i][i].priceOnOrig;
								}


						}
					}

				}

			}

	}


	void interference(String filename, int timeofgraph) throws IOException{
		String tmp;
		String a, b;
		String[] res = null;
		String[] res1 = null;
		int c,d, de;
		int i1=0;
		//int j1=0;
		FileReader input1 = new FileReader(filename);
		BufferedReader bufRead1 = new BufferedReader(input1);
		tmp = bufRead1.readLine();


		while (tmp != null) {
			if (tmp != null) {

				res = tmp.split("\\s");
				a = res[0];
				b = res[1];
			
				//res1 = a.split(",");

				//		c = Integer.parseInt(res1[0]);
				//	d = Integer.parseInt(res1[1]);

				//for (int i=0; i<=NumVert; i++ )
				//	for (int j=0; j<=NumVert; j++ ){
				//		System.out.println(i + "," + j );
				//	if (Ograph[i][j] != null){
				//  if((Ograph[i][j].src==c)&&(Ograph[i][j].dst==d))
				de= 1;//Ograph[i][j].t;
				//System.out.println(de);
				for (int t=0;t< timeofgraph-de;t++)
				{
					for(int t1=t; t1<=t+de; t1++ )
					{   
						beta beta1=new beta (a, b, t, t1, .01);
				//	System.out.println(t1 );
						//System.out.println(Ograph[i][j].src + "," + t +"," + t1 + "," +Ograph[i][j].dst );
						//System.out.println(i1);

						bgraph[i1]=beta1;
						i1++; 
					}}	


				tmp = bufRead1.readLine();

			}
			//	}



		}

	}





}











public class Infocom {

	public static int BFSIZE=4500; 
	static int z, T, E;
	static int numberofcommodities = 10;
	static int Numberofruns=1;
	static int timeofT=10;
	static int rangeofT=0;

	public static void main(String[] args) throws IOException {
		int src[]= new int [numberofcommodities];
		int dest[]= new int [numberofcommodities];
		int i;
		long start = System.currentTimeMillis();;
		int GRAPHSIZE=30;
		int BUFFER=30;
		int srcnoofpaths[]=new int [numberofcommodities];
		src[0]=0;
		src[1]=4;

		src[2]=13;
		src[3]=10;
		src[4]=13;

		src[5]=33;
		src[6]=20;
		src[7]=24;
		src[8]=27;
		src[9]=30;
		/*	src[10]=13;
	src[11]=33;
	src[12]=20;
	src[13]=24;
	src[14]=27;
		 */
		dest[0]=53;
		dest[1]=59;

		dest[2]=56;
		dest[3]=53;
		dest[4]=53;

		dest[5]=53;
		dest[6]=59;
		dest[7]=56;
		dest[8]=50;
		dest[9]=59;
		/* dest[10]=56;
    dest[11]=53;
    dest[12]=53;
    dest[13]=53;
    dest[14]=59;
		 */

		for(int numrun=0;numrun< Numberofruns; numrun++)
			//		for(BUFFER=1 ; BUFFER < 10; BUFFER=BUFFER+2)
		{

			int timeofgraph=timeofT + rangeofT;
			// rangeofT=rangeofT+2;
			String filename = "smallnet430.txt";
			// + BUFFER + GRAPHSIZE+ ".txt" ;
			//System.out.println(filename);
			graph G = new graph(filename, timeofgraph);
			String filename1="conflictnet430.txt";


			G.interference(filename1 , timeofgraph);
			// Read in number of commodities
			flowC[] comm = new flowC[numberofcommodities];
			// Read in commodities and requirements-comm is the array of commodities





			for (i = 0; i < numberofcommodities; i++) {

				Random r = new Random();
				int req = r.nextInt(100 - 10) + 10;
				src[i] = src[i] * G.NumCopies;
				dest[i] = ((dest[i]+1) * G.NumCopies) - 1;
				System.out.println("req="+req);
				flowC fi = new flowC(req, src[i], dest[i]);
				comm[i] = fi;
				srcnoofpaths[i]=0;
				// replace with demand

			}

			double threshold = .01;
			int numcom = 0;
			double delta = 2;
			boolean done = false;
			boolean[] stability = new boolean[numberofcommodities];
			for (i = 0; i < numberofcommodities; i++)
				stability[i] = false;

			outerloop : 	while (!done) {
				done = true;
				innerloop :	for (numcom = 0; numcom < numberofcommodities; numcom++) {

					if (comm[numcom].flowValue() <= comm[numcom].req  - delta) {
					//	System.out.println("entering if");
						done = false;
						G.PriceEdges();
						double callbforddelay[][] = new double[BFSIZE][BFSIZE];
						int callbfordcost[][] = new int[BFSIZE][BFSIZE];

						for (int a = 0; a < G.NumVert * G.NumCopies; a++)
						{for (int b = 0; b < G.NumVert * G.NumCopies; b++) {

							callbfordcost[a][b] = 0;
							if (G.tGraph[a][b] != null)
							{
								callbforddelay[a][b] = G.tGraph[a][b].priceOnOrig;
								//	System.out.println("callbforddelay["+a+"]["+b+"]="+ callbforddelay[a][b]);
							}
							else
							{
								callbforddelay[a][b] = -1;
							}
						}
						}

						int callbfordn = G.NumVert * G.NumCopies;
						int callbfordm = G.NumEdges * G.NumCopies;
						int callbfordsink = comm[numcom].dest;
						int callbfordsource = comm[numcom].src;

						ArrayList PathfromBF = DistributedBellmanFordj
								.ShortestDelayPaths(callbfordn, callbfordm,
										callbfordsource, callbfordsink,
										callbfordcost, callbforddelay);
						path npath = path.convertSPathtofpath(PathfromBF, G);
						// When you convert path make sure flow variables are 0



						System.out.print("Path for commodity "+ numcom + ";");		
						System.out.println(PathfromBF + ";");

						//	System.out.println(npath.source);
						if(npath==null)
							break innerloop;
						if(npath!=null)
						{
							int addid = comm[numcom].addPath(npath);// adds a path
							double flowUp = comm[numcom].UpdateFlowOnPath(addid);
							double zetUp=comm[numcom].UpdatezetOnPath(addid);
							srcnoofpaths[numcom]++;
							System.out.println(comm[numcom].totalflow+ ";");
							//System.out.println("zetup="+ zetUp);
							//System.out.println("total zet="+comm[numcom].totalzet+ ";");
							if ((Math.abs(flowUp) <= threshold)&&(Math.abs(zetUp) <= threshold)) {

								stability[numcom] = true;
								int stabcount = 0;
								for (int stab = 0; stab < numberofcommodities; stab++) {
									if (stability[stab] == true)
									{
										stabcount++;

										System.out.print("commodity" + numcom + "\t is stable"	+ "\t");
										System.out.println(comm[numcom].totalflow+ ";");
										System.out.println(srcnoofpaths[numcom]+ ":");
									}
								}

								if (stabcount == numberofcommodities)
								{ 
									for(int numcomms=0; numcomms< numberofcommodities; numcomms++)
									{
										System.out.print("commodity" + numcom + "\t is stable"
												+ "\t");
										System.out.println(comm[numcom].totalflow);
									}
									System.out.println("T=" + timeofgraph );
									//numcomm++;
									if(timeofgraph == Numberofruns + timeofT - 1)
									{
										long end = System.currentTimeMillis();;
										System.out.println((end - start) + " ms");
										System.exit(0);

									}
									break outerloop;

								}
								//if threshold
							}
						}
						//

						// shortest path call and store in p

					}

				}
			}


		}
	}//end of numrun



}
