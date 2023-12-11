// swift-tools-version: 5.8.1

import PackageDescription

let package = Package(
    name: "Encode-TestDesign-Kata",
    products: [
        .library(
            name: "Encode-TestDesign-Kata",
            targets: ["Encode-TestDesign-Kata"]),
    ],
    targets: [
        .target(
            name: "Encode-TestDesign-Kata"),
        .testTarget(
            name: "Encode-TestDesign-KataTests",
            dependencies: ["Encode-TestDesign-Kata"]),
    ]
)
