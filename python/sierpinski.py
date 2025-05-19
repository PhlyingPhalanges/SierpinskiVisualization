import matplotlib.pyplot as plt
import numpy as np
import time

carpet_ifs = [
  lambda x, y: (x / 3, y / 3),
  lambda x, y: (x / 3, y / 3 + 1 / 3),
  lambda x, y: (x / 3, y / 3 + 2 / 3),
  lambda x, y: (x / 3 + 1 / 3, y / 3),
  lambda x, y: (x / 3 + 2 / 3, y / 3),
  lambda x, y: (x / 3 + 1 / 3, y / 3 + 2 / 3),
  lambda x, y: (x / 3 + 2 / 3, y / 3 + 1 / 3),
  lambda x, y: (x / 3 + 2 / 3, y / 3 + 2 / 3)
]

gasket_ifs = [
  lambda x, y: (x / 2, y / 2),
  lambda x, y: (x / 2 + 1 / 2, y / 2),
  lambda x, y: (x / 2 + 1 / 4, y / 2 + np.sqrt(3) / 4)
]

# Generates Sierpiński Carpet to specified iteration
def carpet(num_iterations):
  iteration = 0
  square = np.array([[0, 0], [0, 1], [1, 1], [1, 0], [0, 0]])
  plt.plot(square[:, 0], square[:, 1], 'ko', markersize=1)
  plt.title("Sierpiński Gasket: Iteration: " + str(iteration))
  plt.axis('equal')
  plt.draw()
  plt.pause(1)
  plt.close()
  iteration += 1

  for i in range(int(num_iterations)):
    new_square = np.zeros((0, 2))
    for point in square:
      for f in carpet_ifs:
        new_square = np.vstack([new_square, f(point[0], point[1])])
    square = new_square
    plt.plot(square[:, 0], square[:, 1], 'ko', markersize=1)
    plt.title("Sierpiński Carpet: Iteration " + str(iteration))
    plt.axis('equal')
    plt.draw()
    plt.pause(1)
    plt.close()
    iteration += 1

# Generates Sierpiński gasket to specified iteration
def gasket(num_iterations):
  iteration = 0
  triangle = np.array([[0, 0], [1 / 2, np.sqrt(3) / 2], [1, 0], [0, 0]])
  plt.plot(triangle[:, 0], triangle[:, 1], 'ko', markersize=1)
  plt.title("Sierpiński Gasket: Iteration: " + str(iteration))
  plt.axis('equal')
  plt.draw()
  plt.pause(1)
  plt.close()
  iteration += 1
  
  for i in range(int(num_iterations)):
    new_triangle = np.zeros((0, 2))
    for point in triangle:
      for f in gasket_ifs:
        new_triangle = np.vstack([new_triangle, f(point[0], point[1])])
    triangle = new_triangle
    plt.plot(triangle[:, 0], triangle[:, 1], 'ko', markersize=1)
    plt.title("Sierpiński Gasket: Iteration: " + str(iteration))
    plt.axis('equal')
    plt.draw()
    plt.pause(1)
    plt.close()
    iteration += 1

# Reads user choices from stdin
def main():
  object_choice = input("Please select the fractal object you would like to see generated:\n[1] Sierpiński Carpet\n[2] Sierpiński Gasket\n")
  time.sleep(1)
  if object_choice == "1":
    choice_show = "Sierpiński Carpet"
  elif object_choice == "2":
    choice_show = "Sierpiński Gasket"
  num_iterations = input("How many iterations of the " + choice_show + " would you like generated?\n")
  if object_choice == "1":
    carpet(int(num_iterations))
  elif object_choice == "2":
    gasket(int(num_iterations))

if __name__ == "__main__":
  main()