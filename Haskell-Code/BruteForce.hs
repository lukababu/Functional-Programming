module BruteForce where

import Data.List
import Data.String

getEmptyList :: [[Int]]
getEmptyList = [[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0]]
{-
-- [[Penalty list]] -> [Forced Partial Assignment] -> [Forbidden Machine] -> [Too-near] -> [Too-near penalities]
getAssignment :: [[Int]] -> [(Int, Int)] -> [(Int, Int)] -> String
getAssignment array fpa _ _ _ = populateFPA array fpa -}

allPossibilities :: [[Int]]
allPossibilities = permutations [0..7]

proccessSet :: (Int, Int) -> [[Int]] -> [[Int]]
proccessSet _ [] = [ ]
proccessSet (a, b) (x:xs)
    | a == 0 && b == x!!0 = proccessSet (a, b) xs
    | a == 1 && b == x!!1 = proccessSet (a, b) xs
    | a == 2 && b == x!!2 = proccessSet (a, b) xs
    | a == 3 && b == x!!3 = proccessSet (a, b) xs
    | a == 4 && b == x!!4 = proccessSet (a, b) xs
    | a == 5 && b == x!!5 = proccessSet (a, b) xs
    | a == 6 && b == x!!6 = proccessSet (a, b) xs
    | a == 7 && b == x!!7 = proccessSet (a, b) xs
    | otherwise = x: proccessSet (a, b) xs
proccessSet (a, b) ( _ :xs) = [1]: proccessSet (a, b) xs

solutionSet :: [(Int, Int)] -> [[Int]] -> [[Int]]
solutionSet fm [] = solutionSet fm allPossibilities
solutionSet [] list = list
solutionSet [x] list = proccessSet x list
solutionSet (x:xs) list = solutionSet xs $ proccessSet x list