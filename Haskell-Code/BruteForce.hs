module BruteForce where

import Data.List
import Data.String

getEmptyList :: [(Int,Int,Int,Int,Int,Int,Int,Int)]
getEmptyList = [(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0),(0,0,0,0,0,0,0,0)]

-- [Forced Partial Assignment] -> [Forbidden Machine] -> [Too-near tasks]
calculateSolutions :: [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)] -> [[Int]]
calculateSolutions fpa fma tnt = solutionSetTNT tnt (solutionSetFPA fpa (solutionSet fma []))

penalty :: Int -> Int -> [(Int,Int,Int,Int,Int,Int,Int,Int)] -> Int
penalty _ _ [] = 0
penalty j i array = penaltyValue i (array!!j)

penaltyValue :: Int -> (Int,Int,Int,Int,Int,Int,Int,Int) -> Int
penaltyValue i (a,b,c,d,e,f,g,h)
    | i == 0 = a
    | i == 1 = b
    | i == 2 = c
    | i == 3 = d
    | i == 4 = e
    | i == 5 = f
    | i == 6 = g
    | i == 7 = h

solutionParse :: Int -> [Int] -> Int
solutionParse i list = list!!i

-- Solution List -> Penalty Table -> Too Near Penalty -> Solutions with quality
calculatePenalty :: [(Int,Int,Int,Int,Int,Int,Int,Int)] -> [[Int]] -> [[Int]]
calculatePenalty _ [] = [ ] 
calculatePenalty table (x:xs) = (x ++ [quality]): calculatePenalty table xs
 where quality = ((penalty 0 (solutionParse 0 x) table)-48) + ((penalty 1 (solutionParse 1 x) table)-48) + ((penalty 2 (solutionParse 2 x) table)-48) + ((penalty 3 (solutionParse 3 x) table)-48) + ((penalty 4 (solutionParse 4 x) table)-48) + ((penalty 5 (solutionParse 5 x) table)-48) + ((penalty 6 (solutionParse 6 x) table)-48) + ((penalty 7 (solutionParse 7 x) table)-48)

-- Generate all possible solutions
allPossibilities :: [[Int]]
allPossibilities = permutations [0..7]

-- Removes solutions with forced partial assignment
proccessFPA :: (Int, Int) -> [[Int]] -> [[Int]]
proccessFPA _ [] = [ ]
proccessFPA (a, b) (x:xs)
    | a == 0 && b == x!!0 = x: proccessFPA (a, b) xs
    | a == 1 && b == x!!1 = x: proccessFPA (a, b) xs
    | a == 2 && b == x!!2 = x: proccessFPA (a, b) xs
    | a == 3 && b == x!!3 = x: proccessFPA (a, b) xs
    | a == 4 && b == x!!4 = x: proccessFPA (a, b) xs
    | a == 5 && b == x!!5 = x: proccessFPA (a, b) xs
    | a == 6 && b == x!!6 = x: proccessFPA (a, b) xs
    | a == 7 && b == x!!7 = x: proccessFPA (a, b) xs
    | otherwise = proccessFPA (a, b) xs
--proccessFPA (a, b) ( _ :xs) = [1]: proccessFPA (a, b) xs

-- Removes solutions with forbidden machine
proccessFM :: (Int, Int) -> [[Int]] -> [[Int]]
proccessFM _ [] = [ ]
proccessFM (a, b) (x:xs)
    | a == 0 && b == x!!0 = proccessFM (a, b) xs
    | a == 1 && b == x!!1 = proccessFM (a, b) xs
    | a == 2 && b == x!!2 = proccessFM (a, b) xs
    | a == 3 && b == x!!3 = proccessFM (a, b) xs
    | a == 4 && b == x!!4 = proccessFM (a, b) xs
    | a == 5 && b == x!!5 = proccessFM (a, b) xs
    | a == 6 && b == x!!6 = proccessFM (a, b) xs
    | a == 7 && b == x!!7 = proccessFM (a, b) xs
    | otherwise = x: proccessFM (a, b) xs
--proccessFM (a, b) ( _ :xs) = [1]: proccessFM (a, b) xs

-- Too near penalities
proccessTNT :: (Int, Int) -> [[Int]] -> [[Int]]
proccessTNT _ [] = [ ]
proccessTNT (a, b) (x:xs)
    | (a == x!!0) && (b == (x!!1)) = proccessTNT (a, b) xs
    | (a == x!!1) && (b == (x!!2)) = proccessTNT (a, b) xs
    | (a == x!!2) && (b == (x!!3)) = proccessTNT (a, b) xs
    | (a == x!!3) && (b == (x!!4)) = proccessTNT (a, b) xs
    | (a == x!!4) && (b == (x!!5)) = proccessTNT (a, b) xs
    | (a == x!!5) && (b == (x!!6)) = proccessTNT (a, b) xs
    | (a == x!!6) && (b == (x!!7)) = proccessTNT (a, b) xs 
    | (a == x!!7) && (b == (x!!0)) = proccessTNT (a, b) xs 
    | (a == x!!1) && (b == (x!!0)) = proccessTNT (a, b) xs
    | (a == x!!2) && (b == (x!!1)) = proccessTNT (a, b) xs
    | (a == x!!3) && (b == (x!!2)) = proccessTNT (a, b) xs
    | (a == x!!4) && (b == (x!!3)) = proccessTNT (a, b) xs
    | (a == x!!5) && (b == (x!!4)) = proccessTNT (a, b) xs
    | (a == x!!6) && (b == (x!!5)) = proccessTNT (a, b) xs
    | (a == x!!7) && (b == (x!!6)) = proccessTNT (a, b) xs
    | (a == x!!0) && (b == (x!!7)) = proccessTNT (a, b) xs
    | otherwise = x: proccessTNT (a, b) xs

-- Takes the set of conditions and parses the solution set 
solutionSet :: [(Int, Int)] -> [[Int]] -> [[Int]]
solutionSet fm [] = solutionSet fm allPossibilities
solutionSet [] list = list
solutionSet [x] list = proccessFM x list
solutionSet (x:xs) list = solutionSet xs $ proccessFM x list

-- Takes the set of conditions and parses the solution set 
solutionSetFPA :: [(Int, Int)] -> [[Int]] -> [[Int]]
solutionSetFPA fm [] = solutionSetFPA fm allPossibilities
solutionSetFPA [] list = list
solutionSetFPA [x] list = proccessFPA x list
solutionSetFPA (x:xs) list = solutionSetFPA xs $ proccessFPA x list

-- Takes the set of conditions and parses the solution set 
solutionSetTNT :: [(Int, Int)] -> [[Int]] -> [[Int]]
solutionSetTNT [] list = list
solutionSetTNT [x] list = proccessTNT x list
solutionSetTNT (x:xs) list = solutionSetTNT xs $ proccessTNT x list

--Takes the matrix and the [position] return an element in the matrix.
getMachPen:: [[Int]] -> [Int] -> Int
getMachPen i j
        |null j == True                 = -1
        |otherwise                      = i!!(j!!0)!!(j!!1)
{-
-- Takes the set of conditions and parses the solution set 
solutionSet :: [(Int, Int)] -> [[Int]] -> [[Int]]
solutionSetTNT [] list = list
solutionSetTNT [x] list = proccessTNT x list
solutionSetTNT (x:xs) list = solutionSetTNT xs $ proccessTNT x list
-}