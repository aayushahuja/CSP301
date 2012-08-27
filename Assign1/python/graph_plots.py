# There are various interesting things that you can in Python using Matplotlib
# and Pyplot. These are libraries which bring Matlab-like functionalities
# to Python. To use these, first make sure that you have the following

  ### 1. Python installation
  ###   -- Already present on Ubuntu, Install from python.org on Windows
  ### 2. Numpy
  ### 3. Matplotlib
  ###   -- Download from Matplotlib. 
  ###   -- On Ubuntu, type sudo apt-get install python-matplotlib on terminal
  ### 4. libpng
  ### 5. freetype

# The steps might vary depending on your OS. Based on your visualization and
# specific requirement, you will want to use the different API calls provided
# by Matplotlib which can be seen http://matplotlib.sourceforge.net/api

# Essentially, you will most commonly use the `plot` and `hist` functions, and
# you can refer the following demo below.
import matplotlib.pyplot as plt
from commands import getoutputb


def hist_demo(X_values):
  ## X_values is the set of x_values whose histogram is to be plotted
  ## Histogram is spread between max-min value of X_values (1 and 12 in 
  ## this case), and number of bins determine the width of each bin (uniform)
  plt.hist(X_values, bins=5, normed=1, facecolor='g', alpha=0.75)
  plt.xlabel('Your X Label Here')
  plt.ylabel('Frequency of X Label Here')
  plt.title('The title of this histogram plot')
  plt.show()

def lineplot_demo(X_values, Y_values):
  ## Plots the X_values and Y_values as a line curve. X_values and Y_values
  ## must be arrays of same size, and the function to be plotted is specified
  ## as f(X_values[i]) = Y_values[i] for all i
  
  ## r-- represents a red-dashed line. Others can be made by choosing a color
  ## like g, b, m, y, etc and taking a symbol like -, x, o
  ## for eg., 'gx' would show green crosses to mark points
  plt.plot(X_values, Y_values, 'r--')
  
  plt.xlabel('Label for X Values')
  plt.ylabel('Label for Y Values')
  plt.title('Dependence of X on Y')
  plt.show()
  
def multilineplot_demo(X_values, Y_values1, Y_values2):
  plt.plot(X_values, Y_values1, 'r--', X_values, Y_values2, 'bo')
  
  plt.xlabel('Label for X Values')
  plt.ylabel('Label for Y Values')
  plt.title('Dependence of X on Y')
  plt.legend(('Legend Marker for Y1', 'Legend Marker for Y2'), loc='bottom right')
  plt.show()


# you'll have to write your own parser for GML file in Python!
def file_reading():
  infile = open('Input File Name', 'r')
  FILE = infile.read() # FILE is a python string
  for line in FILE.split('\n'):
    # do something with this line
    pass
  infile.close() # remember to close!

def file_writing():
  infile = open('Input File Name', 'w')
  FILE = infile.write('Line to be written')
  infile.close() # remember to close!

if __name__ == '__main__':
  hist_demo([1,2,3,4,5,5,5,5,7,8,9])
  # you can call the other functions from here with appropriate values
  
  # another useful trick is to execute programs directly from python
  
  # cmd = 'java Your-Java-Program'
  # output = getoutput(cmd)
  
  # output gets the result as a Python string, which can be parsed to get the
  # results.
