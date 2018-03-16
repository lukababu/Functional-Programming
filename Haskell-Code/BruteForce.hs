module BruteForce where


-- [[Penalty list]] -> [Forced Partial Assignment] -> [Forbidden Machine] -> [Too-near] -> [Too-near penalities]
getAssignment :: [[Int]] -> [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)] -> String
getAssignment array fpa _ _ _ = populateFPA array fpa

populateFPA :: [[Int]] -> [(Int, Int)] -> [[Int]]
populateFPA array [] = array
populateFPA array fpa = [ [ x | x <- xs, even x ] | xs <- array]

getEmptyList :: [[int]]
getEmptyList = [[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0]]

to2D :: [(Int, Int)] -> [[Int]]
to2D fpa = [ [ col | col <- row, even col ] | row <- getEmptyList]
