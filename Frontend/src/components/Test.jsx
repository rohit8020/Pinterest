

const Test = () => {
    return (
      <div className="max-w-xs rounded overflow-hidden shadow-lg">
        <img className="w-full" src="data:image/jpeg;base64,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" alt="No Imag" />
        <div className="px-6 py-4">
          <div className="font-bold text-xl mb-2">Nature</div>
          <p className="text-gray-700 text-base">By Rohit</p>
        </div>
      </div>
    );
  };
  
  export default Test;
  